package com.waldhauser.tasklist.config.support;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.UUID;

/**
 * JwtChecker is a utility class responsible for validating and extracting information
 * from JWT (JSON Web Token) tokens. It is designed to work with a provided cryptographic key
 * for secure processing of tokens.
 *
 * Responsibilities:
 * - Validates the authenticity and structure of JWT tokens using the configured signing key.
 * - Extracts the user identifier (UUID) embedded within the token's claims.
 *
 * Dependencies:
 * - JwtKeyProvider: Supplies the cryptographic key required for token verification, ensuring
 *   that the tokens are signed and can be securely validated.
 *
 * Thread Safety:
 * - The class is thread-safe due to its stateless design and immutable dependencies.
 * - The signing key provided during instantiation is final and does not change during the
 *   lifecycle of the object.
 */
@Component
public class JwtChecker {

    private final Key key;

    /**
     * Constructor for the JwtChecker class.
     * This method initializes the JwtChecker with a signing key obtained from the provided JwtKeyProvider.
     *
     * @param keyProvider An instance of JwtKeyProvider that supplies the cryptographic key
     *                    needed for validating JWT tokens.
     */
    public JwtChecker(JwtKeyProvider keyProvider) {
        this.key = keyProvider.key;
    }

    /**
     * Validates a JWT token by checking its structure and verifying its signature using the configured signing key.
     * This method determines whether the token is properly signed and not tampered with.
     *
     * @param token the JWT token to validate
     * @return {@code true} if the token is valid, otherwise {@code false}
     */
    public boolean isValid(String token) {
        //System.out.println("Raw token: "+ token);
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts the user identifier (UUID) from the claims of a JWT (JSON Web Token).
     * This method parses the provided token, validates its signature using the configured signing key,
     * and retrieves the subject (user ID) stored in the token's claims.
     *
     * @param token the JWT from which the user ID will be extracted
     * @return the UUID representing the user identifier extracted from the token
     */
    public UUID getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return UUID.fromString(claims.getSubject());
    }
}


