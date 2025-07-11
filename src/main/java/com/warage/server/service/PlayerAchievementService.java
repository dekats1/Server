package com.warage.server.service;

import com.warage.server.model.Achievement;
import com.warage.server.model.PlayerAchievement;
import com.warage.server.model.PlayerProfile;
import com.warage.server.repository.AchievementsRepository;
import com.warage.server.repository.PlayerAchievementRepository;
import com.warage.server.repository.PlayerProfileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerAchievementService {

    private PlayerAchievementRepository playerAchievementRepository;
    private AchievementsRepository achievementsRepository;
    private PlayerProfileRepository playerProfileRepository;

    public PlayerAchievementService(PlayerAchievementRepository playerAchievementRepository, AchievementsRepository achievementsRepository, PlayerProfileRepository playerProfileRepository) {
        this.playerAchievementRepository = playerAchievementRepository;
        this.achievementsRepository = achievementsRepository;
        this.playerProfileRepository = playerProfileRepository;
    }

    public List<PlayerAchievement> getAllPlayerAchievements() {return playerAchievementRepository.findAll();}

    public Optional<PlayerAchievement> getPlayersAchievementById(Long id) {return playerAchievementRepository.findById(id);}

    public PlayerAchievement createAchievement(PlayerAchievement achievement) {
        PlayerProfile player = playerProfileRepository.findById(achievement.getPlayer().getPlayerID()).orElseThrow(() -> new RuntimeException("Player not found"));
        Achievement newAchievement = achievementsRepository.findById(achievement.getAchievement().getAchievementID()).orElseThrow(()->new RuntimeException("Achievement not found"));
        achievement.setPlayer(player);
        achievement.setAchievement(newAchievement);
        achievement.setDateAchieved(LocalDateTime.now());
        return playerAchievementRepository.save(achievement);
    }

    public void deleteAchievement(Long id) {
        playerAchievementRepository.deleteById(id);
    }

    public boolean rewardIfCompleted(Long id) {
        PlayerAchievement pa = playerAchievementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Achievement not found"));

        if (pa.getProgress() >= pa.getNeedToReward()) {

            return true;
        }
        return false;
    }
}
