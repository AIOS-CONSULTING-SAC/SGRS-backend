package com.aios.sgrs.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expirationMs = 3600_000; // 1h

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.access}")
    private long expAccess;

    @Value("${jwt.expiration.refresh}")
    private long expRefresh;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Map<String, Object> extraClaims,
                                String subject,
                                TokenType type) {
        long exp = (type == TokenType.ACCESS) ? expAccess : expRefresh;
        Date now = new Date();
        Date expiry = new Date(now.getTime() + exp);

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /** Extract all claims from the JWT (for use in filters). */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /** Validates token expiration and signature. */
    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /** Extracts the subject (username) from the token. */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /** Checks if a token is a refresh token by 'type' claim. */
    public boolean isRefreshToken(String token) {
        return TokenType.REFRESH.name()
                .equals(extractAllClaims(token).get("type", String.class));
    }

}

