package com.waldhauser.tasklist.config.support;

import com.waldhauser.tasklist.domain.model.User;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * JwtMaker is a Spring component responsible for generating JSON Web Tokens (JWT)
 * for authenticated users. It utilizes a cryptographic key and a specified expiration
 * time to create secure tokens that can be used for authentication and authorization purposes.
 * <p>
 * Responsibilities:
 * - Generates a signed JWT for a given user entity.
 * - Embeds the user's unique identifier (UUID) and expiration details in the token.
 * - Configures token expiration time dynamically through application properties.
 * <p>
 * Dependencies:
 * - JwtKeyProvider: Provides the cryptographic key used to sign the tokens.
 * - `@Value("${app.jwt-expiration-milliseconds}")`: Supplies the token expiration time
 *   in milliseconds from the application's configuration.
 * <p>
 * Thread Safety:
 * - The class is thread-safe, as its dependencies are immutable after initialization.
 *   The cryptographic key and expiration time are injected during construction and remain constant.
 */
@Component
public class JwtMaker {

    private final Key key;
    private final long jwtExpirationInMs;

    /**
     * Constructs a new instance of the JwtMaker class with a provided cryptographic key
     * and token expiration time. This class is responsible for generating JWT tokens
     * used for authentication and authorization within the application.
     *
     * @param keyProvider an instance of JwtKeyProvider that supplies the cryptographic key
     *                    used for signing the JWT tokens
     * @param jwtExpirationInMs the duration (in milliseconds) for which the generated JWT tokens
     *                          will remain valid, injected from the application's configuration
     */
    public JwtMaker(JwtKeyProvider keyProvider,
                    @Value("${app.jwt-expiration-milliseconds}") long jwtExpirationInMs) {
        this.key = keyProvider.key;
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    /**
     * Generates a JSON Web Token (JWT) for a specified user. The token includes
     * the user's unique identifier (UUID) as the subject, the current timestamp
     * as the issued at time, and an expiration time determined by the configured
     * expiration duration. The token is signed with a cryptographic key.
     *
     * @param user the user entity for whom the token is to be generated. The user must
     *             have a valid unique identifier (UUID).
     * @return the generated JWT as a String
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString()) // use UUID as subject
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(key)
                .compact();
    }
}