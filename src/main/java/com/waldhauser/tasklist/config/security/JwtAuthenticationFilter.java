package com.waldhauser.tasklist.config.security;

import com.waldhauser.tasklist.config.support.JwtChecker;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * The JwtAuthenticationFilter is a custom filter that processes incoming HTTP requests for JWT-based authentication.
 * It validates the JWT token sent in the Authorization header, extracts the user information if valid,
 * and sets the corresponding authentication in the Spring Security context.
 * <p>
 * This filter extends OncePerRequestFilter, ensuring that it is executed once per request.
 * <p>
 * Responsibilities:
 * - Extract the Authorization header from the incoming request.
 * - Validate the presence and structure of the JWT token (must start with "Bearer ").
 * - Validate the token using the JwtChecker component.
 * - Extract the user ID from the valid token and populate the SecurityContext with an authenticated
 *   UsernamePasswordAuthenticationToken containing a default ROLE_USER authority.
 * - In case of invalid tokens or exceptions during processing, clear the SecurityContext.
 * <p>
 * Dependencies:
 * - JwtChecker: Used for verifying the validity of the JWT token and extracting user-related information.
 * <p>
 * Usage:
 * This filter should be registered within the Spring Security filter chain using methods such as
 * addFilterBefore() to ensure it executes before other security-related filters like the
 * UsernamePasswordAuthenticationFilter.
 * <p>
 * Logging:
 * - Logs successful authentication with the user ID.
 * - Logs invalid tokens and errors during processing for debugging purposes.
 * <p>
 * Thread Safety:
 * - This filter is thread-safe as it does not maintain any mutable state outside of the scope of a single request.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtChecker jwtChecker;

    /**
     * Constructs a new JwtAuthenticationFilter with a specified JwtChecker.
     *
     * @param jwtChecker the JwtChecker instance used to validate and parse JWT tokens
     */
    public JwtAuthenticationFilter(JwtChecker jwtChecker) {
        this.jwtChecker = jwtChecker;
    }

    /**
     * Filters incoming HTTP requests to validate and process JWT tokens for authentication.
     * If a valid JWT token is present in the "Authorization" header, the appropriate authentication is set in the
     * Spring Security context. If the token is invalid or absent, the request proceeds without authentication.
     *
     * @param request the HttpServletRequest object containing client request information
     * @param response the HttpServletResponse object for sending response back to the client
     * @param filterChain the FilterChain object used to pass the request and response to the next filter
     * @throws ServletException if an issue occurs during the request processing
     * @throws IOException if an I/O error occurs during the request processing
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);

        try {
            if (jwtChecker.isValid(token)) {
                UUID userId = jwtChecker.getUserIdFromToken(token);

                // For simplicity, assign a default ROLE_USER authority
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userId.toString(), // principal (user identifier)
                                null,              // credentials (not needed here)
                                authorities        // granted authorities
                        );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                logger.debug("JWT authentication successful for userId: {}", userId);
            } else {
                logger.debug("Invalid JWT token");
            }
        } catch (Exception e) {
            logger.error("JWT processing failed: {}", e.getMessage());
            // Optionally clear context or handle exception
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
