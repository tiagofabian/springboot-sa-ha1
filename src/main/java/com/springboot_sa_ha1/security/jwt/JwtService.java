package com.springboot_sa_ha1.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final Key signingKey;
    private final long expirationMs;

    public JwtService(Environment env) {
        // 1) Tomar primero del Environment (incluye spring-dotenv), luego del OS
        String secret = env.getProperty("JWT_SECRET");
        if (secret == null || secret.isBlank()) secret = System.getenv("JWT_SECRET");

        String expStr = env.getProperty("JWT_EXPIRATION_MS");
        if (expStr == null || expStr.isBlank()) expStr = System.getenv("JWT_EXPIRATION_MS");

        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT_SECRET no está definido (ENV o .env)");
        }
        if (expStr == null || expStr.isBlank()) {
            throw new IllegalStateException("JWT_EXPIRATION_MS no está definido (ENV o .env)");
        }

        long exp;
        try { exp = Long.parseLong(expStr); }
        catch (NumberFormatException e) { throw new IllegalStateException("JWT_EXPIRATION_MS debe ser numérico (ms)"); }
        if (exp <= 0) throw new IllegalStateException("JWT_EXPIRATION_MS debe ser > 0");

        // 2) Intentar Base64; si falla, usar texto plano (requiere >= 32 bytes)
        Key key;
        try {
            byte[] decoded = Base64.getDecoder().decode(secret);
            if (decoded.length < 32) throw new IllegalArgumentException("Base64 < 32 bytes");
            key = Keys.hmacShaKeyFor(decoded);
        } catch (IllegalArgumentException notBase64) {
            byte[] raw = secret.getBytes(StandardCharsets.UTF_8);
            if (raw.length < 32) {
                throw new IllegalStateException("JWT_SECRET debe tener al menos 32 bytes (o usar Base64 de 32+ bytes)");
            }
            key = Keys.hmacShaKeyFor(raw);
        }

        this.signingKey = key;
        this.expirationMs = exp;
    }

    public String generateToken(String subject) { return generateToken(subject, Map.of()); }

    public String generateToken(String subject, Map<String, Object> claims) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(signingKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        try {
            var claims = Jwts.parserBuilder().setSigningKey(signingKey).build()
                    .parseClaimsJws(token).getBody();
            return username.equalsIgnoreCase(claims.getSubject()) && claims.getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
