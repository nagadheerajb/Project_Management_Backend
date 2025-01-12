package fs19.java.backend.config;

import fs19.java.backend.domain.entity.User;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import fs19.java.backend.presentation.shared.exception.AuthenticationNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

/**
 * JWT Helper for validating information
 */
@Component
public class JwtValidator {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    private static final Logger logger = LogManager.getLogger(JwtValidator.class);

    public String extractUserEmail(String token) {
        token = removeBearerPrefix(token); // Remove Bearer prefix
        logger.info("Extracting user email from token: {}", token);
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails systemUserDetails, String workspaceId) {
        token = removeBearerPrefix(token);
        logger.info("Validating token: {}", token);

        final String userEmail = extractUserEmail(token);
        boolean isFound = true;
        List<UUID> workspace_ids = extractPermissions(token);
        if (workspace_ids != null || workspace_ids.contains(workspaceId)) {
            isFound = workspace_ids.contains(workspaceId);
        }
        final Date tokenExpirationDate = extractClaim(token, Claims::getExpiration);
        boolean usernameMatch = Objects.equals(userEmail, systemUserDetails.getUsername());
        boolean tokenIsExpired = tokenExpirationDate.before(DateAndTime.getCurrentDate());

        logger.info("Token validation result - Username match: {}, Token expired: {}, Workspace valid: {}",
                usernameMatch, !tokenIsExpired, isFound);

        return usernameMatch && !tokenIsExpired && isFound;
    }

    public List<UUID> extractPermissions(String token) {
        token = removeBearerPrefix(token); // Remove Bearer prefix
        logger.info("Extracting permissions from token: {}", token);

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
        token = removeBearerPrefix(token); // Remove Bearer prefix

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        token = removeBearerPrefix(token); // Remove Bearer prefix

        try {
            return Jwts
                    .parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception ex) {
            logger.error("Error extracting claims from token: {}", ex.getMessage());
            throw new AuthenticationNotFoundException(ex.getMessage());
        }
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String removeBearerPrefix(String token) {
        if (token.startsWith("Bearer ")) {
            logger.info("Removing 'Bearer ' prefix from token");
            return token.substring(7); // Remove "Bearer " prefix
        }
        return token.trim(); // Ensure no whitespace or unnecessary prefixes
    }

    public boolean isTokenValidForNoWorkspace(String token, UserDetails userDetails) {
        token = removeBearerPrefix(token); // Remove Bearer prefix
        logger.info("Validating token (no workspace check): {}", token);

        String userEmail = extractUserEmail(token);
        final Date tokenExpirationDate = extractClaim(token, Claims::getExpiration);

        boolean usernameMatches = Objects.equals(userEmail, userDetails.getUsername());
        boolean tokenIsExpired = tokenExpirationDate.before(DateAndTime.getCurrentDate());

        // We skip the workspace logic here
        logger.info("Token validation result (no workspace check) - Username match: {}, Token expired: {}",
                usernameMatches, !tokenIsExpired);

        return usernameMatches && !tokenIsExpired;
    }
}
