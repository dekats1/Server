package com.warage.server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "enemies")
public class Enemy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enemy_id")
    private Integer enemyID;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false,name = "base_health")
    private Integer baseHealth;

    @Column(nullable = false)
    private Double speed;

    @Column(nullable = false,name = "reward_coins")
    private Integer rewardCoins;

    @Column(nullable = false,name = "reward_experience")
    private Integer rewardExperience;

    private String assetPath;

    @Column(columnDefinition = "tinyint(1) default 0",name = "is_invisible")
    private Boolean isInvisible;
}