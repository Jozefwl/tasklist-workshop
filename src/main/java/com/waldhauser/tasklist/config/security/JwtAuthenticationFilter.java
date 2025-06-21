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

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtChecker jwtChecker;

    public JwtAuthenticationFilter(JwtChecker jwtChecker) {
        this.jwtChecker = jwtChecker;
    }

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
