package com.warage.server.model;// src/main/java/com/yourgame/warage/model/PlayerProfile.java


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "player_profiles")
public class PlayerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // Убедитесь, что username уникален и не null
    private String username;

    @Column(unique = true) // Email тоже должен быть уникальным
    private String email;

    @Column(nullable = false) // Пароль не должен быть null
    private String hashedPassword; // Храним хешированный пароль

    private int coins;
    private int experience;
    private LocalDateTime lastLogin;

    public PlayerProfile() {
    }

    // Обновленный конструктор
    public PlayerProfile(String username, String email, String hashedPassword, int coins, int experience) {
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.coins = coins;
        this.experience = experience;
        this.lastLogin = LocalDateTime.now();
    }

    // --- Геттеры и Сеттеры ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "PlayerProfile{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", hashedPassword='[PROTECTED]'" + // Не выводите хешированный пароль в логах!
                ", coins=" + coins +
                ", experience=" + experience +
                ", lastLogin=" + lastLogin +
                '}';
    }
}