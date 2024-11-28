package fs19.java.backend.config;

import fs19.java.backend.domain.entity.User;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import fs19.java.backend.presentation.shared.exception.AuthenticationNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

/**
 * JWT Helper for validate information
 */
@Component
public class JwtValidator {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails systemUserDetails, String workspaceId) {
        final String userEmail = extractUserEmail(token);
        boolean isFound = true;
        List<UUID> workspace_ids = extractPermissions(token);
        if (workspace_ids != null || workspace_ids.contains(workspaceId)) {
            isFound = workspace_ids.contains(workspaceId);
        }
        final Date tokenExpirationDate = extractClaim(token, Claims::getExpiration);
        boolean usernameMatch = Objects.equals(userEmail, systemUserDetails.getUsername());
        boolean tokenIsExpired = tokenExpirationDate.before(DateAndTime.getCurrentDate());
        return usernameMatch && !tokenIsExpired && isFound;
    }

    public List<UUID> extractPermissions(String token) {
        Jws<Claims> claimsJws = Jwts
                .parser()
                .verifyWith(getSignInKey()) // Use the same signing key
                .build()
                .parseSignedClaims(token);

        // Get claims
        Claims claims = claimsJws.getPayload();
        return (List<UUID>) claims.get("permission");
    }

    public String generateToken(User user, List<UUID> workspacePermission) {
        Map<String, List<UUID>> userClaims = new HashMap<>();
        userClaims.put("permission", workspacePermission);
        return generateTokenByClaims(userClaims, user);
    }

    public String generateTokenByClaims(Map<String, List<UUID>> extraClaims, User systemUserDetails) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(systemUserDetails.getEmail())
                .issuedAt(DateAndTime.getCurrentDate())
                .expiration(DateAndTime.getExpirationDate())
                .signWith(getSignInKey())
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception ex) {
            throw new AuthenticationNotFoundException(ex.getMessage());
        }
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}