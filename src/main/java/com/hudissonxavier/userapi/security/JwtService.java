package com.hudissonxavier.userapi.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {

    @Value("${api.security.token.secret}")
    private String secret;

    private SecretKey key;

    // Inicializa a key após injeção do secret
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Gera token JWT com id, email e nome
    public String generateToken(UUID userId, String email, String name) {
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(email) // email como subject
                .claim("id", userId.toString()) // adiciona id
                .claim("name", name) // adiciona nome
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(1, ChronoUnit.DAYS)))
                .signWith(key)
                .compact();
    }

    // Extrai o email (subject)
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    // Extrai o ID do usuário
    public UUID extractUserId(String token) {
        String id = extractClaims(token).get("id", String.class);
        return UUID.fromString(id);
    }

    // Extrai o nome do usuário
    public String extractName(String token) {
        return extractClaims(token).get("name", String.class);
    }

    // Valida se o token é válido (não expirado e assinatura correta)
    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}