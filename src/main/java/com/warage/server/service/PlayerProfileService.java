package com.warage.server.service;// src/main/java/com/yourgame/warage/service/PlayerProfileService.java

import com.warage.server.model.PlayerProfile;
import com.warage.server.repository.PlayerProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service // Указывает, что это компонент сервиса
public class PlayerProfileService {

    private final PlayerProfileRepository playerProfileRepository;

    // Внедрение зависимости через конструктор (рекомендуемый способ)
    public PlayerProfileService(PlayerProfileRepository playerProfileRepository) {
        this.playerProfileRepository = playerProfileRepository;
    }

    @Transactional(readOnly = true) // Транзакция только для чтения
    public Optional<PlayerProfile> getPlayerProfileById(Long id) {
        return playerProfileRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<PlayerProfile> getPlayerProfileByUsername(String username) {
        return playerProfileRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<PlayerProfile> getAllPlayerProfiles() {
        return playerProfileRepository.findAll();
    }

    @Transactional // Открытие транзакции для записи
    public PlayerProfile createPlayerProfile(String username) {
        // Проверяем, существует ли пользователь с таким именем
        if (playerProfileRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("User with username " + username + " already exists.");
        }
        PlayerProfile newProfile = new PlayerProfile(username, 0, 0); // Новые игроки начинают с 0 монет/опыта
        return playerProfileRepository.save(newProfile);
    }

    @Transactional
    public PlayerProfile updatePlayerProfile(Long id, PlayerProfile updatedProfile) {
        return playerProfileRepository.findById(id)
                .map(profile -> {
                    profile.setCoins(updatedProfile.getCoins());
                    profile.setExperience(updatedProfile.getExperience());
                    profile.setLastLogin(LocalDateTime.now()); // Обновляем время последнего входа
                    // Можно добавить другие поля для обновления
                    return playerProfileRepository.save(profile);
                })
                .orElseThrow(() -> new IllegalArgumentException("Player profile with ID " + id + " not found."));
    }

    @Transactional
    public void deletePlayerProfile(Long id) {
        playerProfileRepository.deleteById(id);
    }

    @Transactional
    public PlayerProfile loginPlayer(String username) {
        return playerProfileRepository.findByUsername(username)
                .map(profile -> {
                    profile.setLastLogin(LocalDateTime.now());
                    return playerProfileRepository.save(profile);
                })
                .orElseGet(() -> {
                    // Если пользователя нет, создаем новый профиль
                    System.out.println("Creating new player profile for: " + username);
                    return createPlayerProfile(username);
                });
    }
}