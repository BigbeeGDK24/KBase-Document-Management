package com.kbase.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {

        this.jwtFilter = jwtFilter;

        System.out.println(">>> SECURITY CONFIG LOADED");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                // REST API
                .csrf(
                        csrf -> csrf.disable()
                )
                // CORS
                .cors(
                        cors -> {
                        }
                )
                .authorizeHttpRequests(auth -> auth
                        // ==========================
                        // AUTH
                        // Login + Register
                        // ==========================

                        .requestMatchers(
                                "/auth/**"
                        )
                        .permitAll()
                        // ==========================
                        // SWAGGER
                        // ==========================

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        )
                        .permitAll()
                        // ==========================
                        // ARTICLES
                        // ==========================

                        // Xem bài viết
                        .requestMatchers(
                                HttpMethod.GET,
                                "/articles/**"
                        )
                        .permitAll()
                        // Tạo bài viết
                        .requestMatchers(
                                HttpMethod.POST,
                                "/articles"
                        )
                        .authenticated()
                        // Sửa bài viết
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/articles/**"
                        )
                        .authenticated()
                        // Xóa bài viết
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/articles/**"
                        )
                        .authenticated()
                        // ==========================
                        // PROJECT
                        // ==========================

                        // Tạm mở để test Postman
                        // Sau này đổi thành authenticated()
                        .requestMatchers(
                                "/projects/**"
                        )
                        .authenticated()
                        // ==========================
                        // OTHER API
                        // ==========================

                        .anyRequest()
                        .authenticated()
                )
                // JWT Filter chạy trước UsernamePasswordAuthenticationFilter
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration
                = new CorsConfiguration();

        configuration.addAllowedOrigin(
                "http://localhost:5173"
        );

        configuration.addAllowedHeader(
                "*"
        );

        configuration.addAllowedMethod(
                "*"
        );

        configuration.setAllowCredentials(
                true
        );

        UrlBasedCorsConfigurationSource source
                = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;

    }

}
