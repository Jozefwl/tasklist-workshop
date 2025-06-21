package com.waldhauser.tasklist.config.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

@Component
public class JwtKeyProvider {

    public final Key key;
    private final String jwtSecret;

    public JwtKeyProvider(@Value("${app.jwt-secret}") String jwtSecret) {
        System.out.println("Loaded secret: " + jwtSecret);
        this.jwtSecret = jwtSecret;
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public Key getKey() {
        return key;
    }
}