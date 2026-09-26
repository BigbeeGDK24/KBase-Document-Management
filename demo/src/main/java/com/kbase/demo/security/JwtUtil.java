package com.kbase.demo.security;

import io.jsonwebtoken.*;

import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

    }

    // tạo token có username + role
    public String generateToken(
            String username,
            String role
    ) {

        return Jwts.builder()
                .setSubject(username)
                .claim(
                        "role",
                        role
                )
                .setIssuedAt(
                        new Date()
                )
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                + 1000 * 60 * 60
                        )
                )
                .signWith(
                        getSigningKey(),
                        SignatureAlgorithm.HS256
                )
                .compact();

    }

    // lấy username
    public String extractUsername(
            String token
    ) {

        return Jwts.parserBuilder()
                .setSigningKey(
                        getSigningKey()
                )
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }

    // lấy role
    public String extractRole(
            String token
    ) {

        return Jwts.parserBuilder()
                .setSigningKey(
                        getSigningKey()
                )
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(
                        "role",
                        String.class
                );

    }

}
