package com.tezivindh.ride_share_backend.config;

import com.tezivindh.ride_share_backend.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Check for Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            try {
                username = jwtUtil.extractUsername(token);
            } catch (Exception e) {
                // Invalid token → just ignore and continue
            }
        }

        // If valid token and no authentication yet
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Extract role directly from JWT
            String role = jwtUtil.extractRole(token); // example: ROLE_USER or ROLE_DRIVER

            if (jwtUtil.validateToken(token, username)) {

                var authToken = new UsernamePasswordAuthenticationToken(
                        username, // principal = username
                        null,
                        List.of(new SimpleGrantedAuthority(role)) // authorities
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}