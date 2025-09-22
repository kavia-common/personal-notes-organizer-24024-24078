package com.example.notesappbackend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

/**
 * Ocean Professional JWT Service: Issues and validates compact tokens.
 */
@Service
public class JwtTokenService {

    private final Key key;
    private final long ttlSeconds;

    public JwtTokenService(
            @Value("${app.jwt.secret:change-this-secret-in-prod-please-use-env}") String secret,
            @Value("${app.jwt.ttl-seconds:3600}") long ttlSeconds
    ) {
        // In production, use a 256-bit+ base64 secret via environment variables
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.ttlSeconds = ttlSeconds;
    }

    // PUBLIC_INTERFACE
    public String generateToken(String username) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(ttlSeconds)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // PUBLIC_INTERFACE
    public String validateAndGetSubject(String token) throws JwtException {
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return jws.getBody().getSubject();
    }
}
