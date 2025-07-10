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
    private Long id;

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

    @Column(name = "lastLogin" )
    private LocalDateTime lastLogin;

    @Column(name = "maxDamage")
    private int maxDamage;

    @Column(name = "endlessHighestWave")
    private int endlessHighestWave;

    @Column(name = "endlessHighScore")
    private Long endlessHighScore;

}