package com.warage.server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "player_profiles")
public class PlayerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "player_id")
    private Long playerID;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String hashedPassword;

    @Column(nullable = false)
    private int money;

    @Column(nullable = false)
    private int experience;

    @Column(name = "last_login" )
    private LocalDateTime lastLogin;

    @Column(name = "max_damage")
    private int maxDamage;

    @Column(name = "endless_highest_wave")
    private int endlessHighestWave;

    @Column(name = "endless_high_score")
    private Long endlessHighScore;
}