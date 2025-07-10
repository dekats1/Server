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
@Table(name = "levels")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer levelID;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer startingCoins;

    private Integer rewardMoney;

    private String mapDataPath;

    private String waveConfigPath;
}