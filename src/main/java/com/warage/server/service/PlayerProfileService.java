package com.warage.server.service;// src/main/java/com/yourgame/warage/service/PlayerProfileService.java

import com.warage.server.model.PlayerProfile;
import com.warage.server.repository.PlayerProfileRepository;
import org.springframework.security.crypto.password.PasswordEncoder; // Импортируем
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerProfileService {

    private final PlayerProfileRepository playerProfileRepository;
    private final PasswordEncoder passwordEncoder; // Внедряем PasswordEncoder

    public PlayerProfileService(PlayerProfileRepository playerProfileRepository, PasswordEncoder passwordEncoder) {
        this.playerProfileRepository = playerProfileRepository;
        this.passwordEncoder = passwordEncoder; // Инициализируем
    }

    @Transactional(readOnly = true)
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

    // Обновленный метод для создания профиля с email и паролем
    @Transactional
    public PlayerProfile registerPlayer(String username, String password, String email) {
        // Проверяем, существует ли пользователь с таким именем или email
        if (playerProfileRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("User with username '" + username + "' already exists.");
        }
        if (playerProfileRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User with email '" + email + "' already exists.");
        }

        // Хешируем пароль перед сохранением
        String hashedPassword = passwordEncoder.encode(password);

        PlayerProfile newProfile = PlayerProfile.builder()
                .username(username)
                .email(email)
                .hashedPassword(hashedPassword)
                .money(0)
                .experience(0)
                .lastLogin(LocalDateTime.now())
                .maxDamage(0)
                .endlessHighestWave(0)
                .endlessHighScore(0L)
                .build();
        return playerProfileRepository.save(newProfile);
    }

    // Исправленный код
    @Transactional
    public PlayerProfile updatePlayerProfile(Long id, PlayerProfile updatedProfile) {
        return playerProfileRepository.findById(id)
                .map(profile -> {
                    profile.setMoney(updatedProfile.getMoney());
                    profile.setExperience(updatedProfile.getExperience()); // <-- Исправлено
                    profile.setLastLogin(LocalDateTime.now());
                    // ...
                    return playerProfileRepository.save(profile);
                })
                .orElseThrow(() -> new IllegalArgumentException("Player profile with ID " + id + " not found."));
    }

    @Transactional
    public void deletePlayerProfile(Long id) {
        playerProfileRepository.deleteById(id);
    }

    // Обновленный метод логина для проверки пароля
    @Transactional
    public PlayerProfile loginPlayer(String username, String password) {
        return playerProfileRepository.findByUsername(username)
                .map(profile -> {
                    if (passwordEncoder.matches(password, profile.getHashedPassword())) {
                        profile.setLastLogin(LocalDateTime.now());
                        return playerProfileRepository.save(profile);
                    } else {
                        throw new IllegalArgumentException("Invalid password for user: " + username);
                    }
                })
                .orElseThrow(() -> new IllegalArgumentException("User '" + username + "' not found."));
    }
}