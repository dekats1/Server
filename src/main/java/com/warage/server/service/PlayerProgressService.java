package com.warage.server.service;

import com.warage.server.model.Level;
import com.warage.server.model.PlayerProgress;
import com.warage.server.model.PlayerProfile;
import com.warage.server.repository.LevelRepository;
import com.warage.server.repository.PlayerProgressRepository;
import com.warage.server.repository.PlayerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerProgressService {

    private final PlayerProgressRepository playerProgressRepository;
    private final PlayerProfileRepository playerProfileRepository; // Добавляем для проверки PlayerProfile
    private final LevelRepository levelRepository; // Добавляем для проверки Level

    @Autowired
    public PlayerProgressService(PlayerProgressRepository playerProgressRepository,
                                 PlayerProfileRepository playerProfileRepository,
                                 LevelRepository levelRepository) {
        this.playerProgressRepository = playerProgressRepository;
        this.playerProfileRepository = playerProfileRepository;
        this.levelRepository = levelRepository;
    }

    public List<PlayerProgress> getAllPlayerProgress() {
        return playerProgressRepository.findAll();
    }

    public Optional<PlayerProgress> getPlayerProgressById(Long id) {
        return playerProgressRepository.findById(id);
    }

    @Transactional
    public PlayerProgress createPlayerProgress(PlayerProgress playerProgress) {
        // Проверяем, существуют ли PlayerProfile и Level перед сохранением
        Long playerId = playerProgress.getPlayer().getPlayerID();
        Integer levelId = playerProgress.getLevel().getLevelID();

        PlayerProfile player = playerProfileRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player with ID " + playerId + " not found."));
        Level level = levelRepository.findById(levelId)
                .orElseThrow(() -> new RuntimeException("Level with ID " + levelId + " not found."));

        playerProgress.setPlayer(player);
        playerProgress.setLevel(level);
        playerProgress.setLastPlayed(LocalDateTime.now()); // Устанавливаем текущее время

        return playerProgressRepository.save(playerProgress);
    }

    @Transactional
    public PlayerProgress updatePlayerProgress(Long id, PlayerProgress playerProgressDetails) {
        return playerProgressRepository.findById(id)
                .map(existingProgress -> {
                    // Обновляем только изменяемые поля
                    existingProgress.setHighestWaveReached(playerProgressDetails.getHighestWaveReached());
                    existingProgress.setScore(playerProgressDetails.getScore());
                    existingProgress.setCompleted(playerProgressDetails.getCompleted());
                    existingProgress.setUnlockedNextLevel(playerProgressDetails.getUnlockedNextLevel());
                    existingProgress.setLastPlayed(LocalDateTime.now()); // Обновляем время последнего изменения

                    // Проверяем и обновляем связанные PlayerProfile и Level, если они изменились.
                    // Обычно, для связей ManyToOne, мы просто обновляем ссылки на существующие объекты.
                    // Если PlayerProfile или Level должны быть изменены, это должно быть сделано через их собственные сервисы.
                    if (playerProgressDetails.getPlayer() != null &&
                            !existingProgress.getPlayer().getPlayerID().equals(playerProgressDetails.getPlayer().getPlayerID())) {
                        PlayerProfile newPlayer = playerProfileRepository.findById(playerProgressDetails.getPlayer().getPlayerID())
                                .orElseThrow(() -> new RuntimeException("Player with ID " + playerProgressDetails.getPlayer().getPlayerID() + " not found."));
                        existingProgress.setPlayer(newPlayer);
                    }

                    if (playerProgressDetails.getLevel() != null &&
                            !existingProgress.getLevel().getLevelID().equals(playerProgressDetails.getLevel().getLevelID())) {
                        Level newLevel = levelRepository.findById(playerProgressDetails.getLevel().getLevelID())
                                .orElseThrow(() -> new RuntimeException("Level with ID " + playerProgressDetails.getLevel().getLevelID() + " not found."));
                        existingProgress.setLevel(newLevel);
                    }

                    return playerProgressRepository.save(existingProgress);
                })
                .orElse(null); // Или выбросьте исключение, например, ResourceNotFoundException
    }

    public boolean deletePlayerProgress(Long id) {
        if (playerProgressRepository.existsById(id)) {
            playerProgressRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<PlayerProgress> getPlayerProgressByPlayerId(Long playerId) {
        PlayerProfile player = playerProfileRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player with ID " + playerId + " not found."));
        return playerProgressRepository.findByPlayer(player);
    }

    public Optional<PlayerProgress> getPlayerProgressByPlayerIdAndLevelId(Long playerId, Integer levelId) {
        PlayerProfile player = playerProfileRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player with ID " + playerId + " not found."));
        Level level = levelRepository.findById(levelId)
                .orElseThrow(() -> new RuntimeException("Level with ID " + levelId + " not found."));
        return playerProgressRepository.findByPlayerAndLevel(player, level);
    }
}