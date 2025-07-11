// src/main/java/com/warage/server/config/SecurityConfig.java
package com.warage.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity // Включает веб-безопасность Spring
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Отключаем CSRF для REST API
                .authorizeHttpRequests(authorize -> authorize
                        // Разрешаем доступ к нашим эндпоинтам регистрации и логина без аутентификации
                        .requestMatchers("/api/v1/players/register", "/api/v1/players/login").permitAll()
                        .requestMatchers("/api/player-achievements/**").permitAll()
                        // Разрешаем доступ к консоли H2 (если вы ее используете)
                        .requestMatchers("/h2-console/**").permitAll()
                        // Все остальные запросы должны быть аутентифицированы
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults()); // Отключаем базовую HTTP аутентификацию (чтобы не было всплывающего окна)
        // .formLogin(withDefaults()); // Если бы вы хотели использовать форму логина Spring Security
        // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Для REST API часто используется STATELESS сессии

        // Для H2-консоли, если она работает во фрейме
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }
}