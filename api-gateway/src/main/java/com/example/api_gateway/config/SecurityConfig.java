//package com.example.api_gateway.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        http
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(exchange -> exchange
//                        // 1. Allow Auth and Public Endpoints
//                        .pathMatchers("/auth/**").permitAll()
//
//                        // 2. Allow Swagger UI Resources (CRITICAL FIX)
//                        .pathMatchers(
//                                "/v3/api-docs/**",
//                                "/swagger-ui/**",
//                                "/swagger-ui.html",
//                                "/webjars/**"
//                        ).permitAll()
//
//                        // 3. Lock everything else
//                        .anyExchange().authenticated()
//                );
//
//        return http.build();
//    }
//}
