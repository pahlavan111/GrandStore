package org.bpf.grandstore.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.bpf.grandstore.config.JwtConfig;
import org.bpf.grandstore.entity.Role;
import org.bpf.grandstore.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class JwtService {

    private final JwtConfig jwtConfig;


    public String generateAccessToken(User user) {
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    private String generateToken(User user, long expirationTime) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("name", user.getName() + " " + user.getLastName())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                .signWith(jwtConfig.getSecretKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            return getClaims(token).getExpiration().after(new Date());
        } catch (JwtException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }

    public Role getUserRoleFromToken(String token) {
        return Role.valueOf(getClaims(token).get("role", String.class));
    }
}
