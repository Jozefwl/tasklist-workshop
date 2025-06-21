package com.waldhauser.tasklist.config.support;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.UUID;

@Component
public class JwtChecker {

    private final Key key;

    public JwtChecker(JwtKeyProvider keyProvider) {
        this.key = keyProvider.key;
    }

    public boolean isValid(String token) {
        //System.out.println("Raw token: "+ token);
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UUID getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return UUID.fromString(claims.getSubject());
    }
}


