// src/main/java/com/yourgame/warage/model/PlayerProfile.java
package com.warage.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity // Указывает, что это JPA-сущность, маппится на таблицу в БД
@Table(name = "player_profiles") // Имя таблицы в БД
@Data
public class PlayerProfile {

    @Id // Указывает, что это первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоматическая генерация ID

    private Long id;

    private String username;
    private int coins;
    private int experience;
    private LocalDateTime lastLogin; // Для отслеживания активности

    // Конструктор по умолчанию (требуется JPA)
    public PlayerProfile() {
    }

    public PlayerProfile(String username, int coins, int experience) {
        this.username = username;
        this.coins = coins;
        this.experience = experience;
        this.lastLogin = LocalDateTime.now();
    }
}