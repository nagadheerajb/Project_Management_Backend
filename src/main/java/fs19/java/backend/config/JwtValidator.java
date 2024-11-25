package fs19.java.backend.config;

import fs19.java.backend.domain.entity.User;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

    public boolean isTokenValid(String token, UserDetails systemUserDetails) {
        final String userEmail = extractUserEmail(token);
        final Date tokenExpirationDate = extractClaim(token, Claims::getExpiration);
        boolean usernameMatch = Objects.equals(userEmail, systemUserDetails.getUsername());
        boolean tokenIsExpired = tokenExpirationDate.before(DateAndTime.getCurrentDate());
        return usernameMatch && !tokenIsExpired;
    }

    public String generateToken(User userDetails) {
        return this.generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, User systemUserDetails) {
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
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
