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


    public Jwt generateAccessToken(User user) {
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public Jwt generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    private Jwt generateToken(User user, long expirationTime) {
        var claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("name", user.getName() + " " + user.getLastName())
                .add("email", user.getEmail())
                .add("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                .build();

        return new Jwt(claims, jwtConfig.getSecretKey());
    }


    public Jwt parseToken(String token){
        try {
            var claims = getClaims(token);
            return new Jwt(claims, jwtConfig.getSecretKey());
        } catch (JwtException e){
            return null;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
