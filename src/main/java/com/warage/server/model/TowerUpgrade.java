package com.warage.server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tower_upgrades")
public class TowerUpgrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer upgradeID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "towerID", nullable = false)
    private Tower tower;

    @Column(nullable = false)
    private Integer upgradePath;

    @Column(nullable = false)
    private Integer upgradeLevel;

    @Column(nullable = false)
    private Integer cost;

    @Column(nullable = false)
    private Integer damage;

    @Column(nullable = false)
    private Double range;

    @Column(nullable = false)
    private Double attackSpeed;

    @Column(columnDefinition = "TEXT")
    private String specialEffectDescription;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean canDetectInvisible;
}