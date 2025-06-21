package com.waldhauser.tasklist.config.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

/**
 * JwtKeyProvider is a Spring component responsible for generating and managing the cryptographic key
 * used for signing and validating JSON Web Tokens (JWT). It uses a secret key, which is obtained
 * from application properties, to create an HMAC-SHA key.
 * <p>
 * Responsibilities:
 * - Generates an HMAC-SHA key from a JWT secret to ensure secure signing and validation of tokens.
 * - Provides access to the generated key and the original secret.
 * <p>
 * Configuration:
 * - The JWT secret is supplied via the `app.jwt-secret` property, which must be configured in the
 *   application properties file.
 * <p>
 * Dependencies:
 * - `Keys.hmacShaKeyFor`: A utility provided by the JJWT library to generate the signing key.
 * <p>
 * Thread Safety:
 * - The class is thread-safe as it only initializes its fields during construction, which are final
 *   and immutable.
 */
@Component
public class JwtKeyProvider {

    public final Key key;
    private final String jwtSecret;

    /**
     * Initializes a new instance of the JwtKeyProvider class with the specified secret key.
     * This constructor generates an HMAC-SHA key for use in signing and validating JSON Web Tokens (JWT).
     * The secret key is injected via the application properties.
     *
     * @param jwtSecret the secret key used to generate the signing key for JWTs,
     *                  provided through the `app.jwt-secret` property in the application configuration.
     */
    public JwtKeyProvider(@Value("${app.jwt-secret}") String jwtSecret) {
        System.out.println("Loaded secret: " + jwtSecret);
        this.jwtSecret = jwtSecret;
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Retrieves the value of the JWT secret key.
     *
     * @return the JWT secret key as a String
     */
    public String getJwtSecret() {
        return jwtSecret;
    }

    /**
     * Retrieves the cryptographic key used for signing and validating JSON Web Tokens (JWT).
     *
     * @return the cryptographic key as a Key object
     */
    public Key getKey() {
        return key;
    }
}