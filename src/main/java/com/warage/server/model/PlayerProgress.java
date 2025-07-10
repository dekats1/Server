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

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "player_progress")
public class PlayerProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long progressID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playerID", nullable = false)
    private PlayerProfile player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "levelID", nullable = false)
    private Level level;

    @Column(nullable = false)
    private Integer highestWaveReached;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private Boolean completed;

    @Column(nullable = false)
    private Boolean unlockedNextLevel;

    private LocalDateTime lastPlayed;
}