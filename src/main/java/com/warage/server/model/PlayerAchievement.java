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
@Table(name = "player_achievements")
public class PlayerAchievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerAchievementID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playerID", nullable = false)
    private PlayerProfile player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievementID", nullable = false)
    private Achievement achievement;

    @Column(nullable = false)
    private LocalDateTime dateAchieved;
}