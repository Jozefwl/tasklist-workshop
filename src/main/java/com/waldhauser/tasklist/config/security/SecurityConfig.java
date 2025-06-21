package com.waldhauser.tasklist.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up Spring Security within the application.
 * It defines security settings, including the configuration for stateless authentication using JWT.
 * This class integrates a custom JwtAuthenticationFilter into the security filter chain.
 * <p>
 * Responsibilities:
 * - Disables Cross-Site Request Forgery (CSRF) protection as JWT is used for stateless authentication.
 * - Configures session management to be stateless, avoiding the use of HTTP sessions.
 * - Sets up authorization rules for various HTTP endpoints:
 *   - Allows access to public endpoints such as authentication (e.g., login, register).
 *   - Requires authentication for protected resources (/task, /tasklist).
 *   - Permits access to all other unspecified endpoints.
 * - Integrates the JwtAuthenticationFilter for processing JWT-based authentication before the default
 *   UsernamePasswordAuthenticationFilter.
 * <p>
 * Dependencies:
 * - JwtAuthenticationFilter: A custom filter responsible for validating and processing JWT tokens
 *   provided with incoming requests.
 * <p>
 * Thread Safety:
 * This class relies on Spring Framework's configuration management and is inherently thread-safe.
 */
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Constructs a new SecurityConfig instance with the specified JwtAuthenticationFilter.
     * The JwtAuthenticationFilter is a custom filter responsible for processing JWT-based authentication
     * within the Spring Security filter chain.
     *
     * @param jwtAuthenticationFilter the JwtAuthenticationFilter instance to be integrated into the security configuration
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configures the security filter chain for handling authentication and authorization within the application.
     *
     * The method defines the security rules and integrates a custom JWT authentication filter into the
     * Spring Security filter chain. It is responsible for disabling CSRF, managing stateless sessions, defining
     * endpoint access rules, and applying the custom JWT filter.
     *
     * @param http the HttpSecurity object used to configure web-based security for specific HTTP requests
     * @return the configured SecurityFilterChain instance
     * @throws Exception if an error occurs while building the security configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF since we use JWT for stateless auth
                .csrf(csrf -> csrf.disable())

                // Stateless session management, no HTTP sessions
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Define URL authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()   // Public endpoints (login, register)
                        .requestMatchers("/task/**", "/tasklist/**").authenticated() // Protected endpoints
                        .anyRequest().permitAll() // Other endpoints accessible without auth
                )

                // Add JWT filter before Spring Security's username/password filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
