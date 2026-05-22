package com.produtoapi.historicoservice.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {

    private final Key signingKey;

    public JwtService(@Value("${JWT_SECRET}") String secret) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String extractUsername(String token) {
        return extractAllClaims(cleanToken(token)).getSubject();
    }

    public String extractRole(String token) {
        return extractAllClaims(cleanToken(token)).get("role", String.class);
    }

    public Long extractUserId(String token) {
        try {
            return Long.valueOf(extractUsername(token));
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(cleanToken(token));
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String cleanToken(String token) {
        return token.startsWith("Bearer ")
                ? token.substring(7)
                : token;
    }
}