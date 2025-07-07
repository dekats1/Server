package com.warage.server.config;// src/main/java/com/yourgame/warage/config/WebConfig.java

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**") // Применяем CORS ко всем эндпоинтам /api/v1/**
                .allowedOrigins("http://localhost:8080", "http://127.0.0.1:8080") // Разрешенные клиенты (ваш JavaFX может работать на 8080)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешенные HTTP методы
                .allowedHeaders("*") // Разрешенные заголовки
                .allowCredentials(true); // Разрешить отправку куки и авторизационных заголовков
    }
}