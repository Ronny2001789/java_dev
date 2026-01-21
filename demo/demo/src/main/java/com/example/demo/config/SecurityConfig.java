package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                //  Disable CSRF (needed for REST APIs, Postman, Swagger)
                .csrf(csrf -> csrf.disable())

                //  Authorization rules
                .authorizeHttpRequests(auth -> auth

                        //  Allow Swagger UI (API documentation)
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        //  Allow H2 database console
                        .requestMatchers("/h2-console/**").permitAll()

                        //  Allow authentication endpoints (register / login)
                        .requestMatchers("/api/auth/**").permitAll()

                        // ️ TEMPORARY: allow everything else (for development)
                        .anyRequest().permitAll()
                )

                //  Fix for H2 console iframe issue
                .headers(headers ->
                        headers.frameOptions(frame -> frame.sameOrigin())
                );

        return http.build();
    }

    //  Password encoder (used when saving users)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
