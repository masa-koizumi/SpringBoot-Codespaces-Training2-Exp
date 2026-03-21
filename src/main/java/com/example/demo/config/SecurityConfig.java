package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/login", "/css/**", "/h2-console/**").permitAll() // ログイン不要なページ
                .anyRequest().authenticated() // それ以外はログインが必要
            )
            .csrf(csrf -> csrf.disable()) // 学習用のため一時的に無効化
            .headers(headers -> headers.frameOptions(frame -> frame.disable())); // H2 Console用

        return http.build();
    }
}
