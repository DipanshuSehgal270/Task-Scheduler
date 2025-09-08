package com.example.userService.config;



import com.example.userService.service.JwtUtil;
import com.example.userService.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        // --- ADD THIS LOG ---
        System.out.println("--- USER-SERVICE FILTER: Checking request to " + request.getRequestURI() + " ---");
        System.out.println("--- USER-SERVICE FILTER: Auth Header: " + authorizationHeader + " ---");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
                // --- ADD THIS LOG ---
                System.out.println("--- USER-SERVICE FILTER: Extracted username: " + username + " ---");
            } catch (Exception e) {
                System.out.println("--- USER-SERVICE FILTER: Error extracting username: " + e.getMessage() + " ---");
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                // --- ADD THIS LOG ---
                System.out.println("--- USER-SERVICE FILTER: Token is valid. Setting security context. ---");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                // --- ADD THIS LOG ---
                System.out.println("--- USER-SERVICE FILTER: Token validation failed! ---");
            }
        }
        chain.doFilter(request, response);
    }
}