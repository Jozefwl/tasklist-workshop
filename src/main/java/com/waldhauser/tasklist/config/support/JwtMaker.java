package com.waldhauser.tasklist.config.support;

import com.waldhauser.tasklist.domain.model.User;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtMaker {

    private final Key key;
    private final long jwtExpirationInMs;

    public JwtMaker(JwtKeyProvider keyProvider,
                    @Value("${app.jwt-expiration-milliseconds}") long jwtExpirationInMs) {
        this.key = keyProvider.key;
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString()) // use UUID as subject
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(key)
                .compact();
    }
}