package com.warage.server.controller;

import com.warage.server.model.Achievement;
import com.warage.server.model.PlayerAchievement;
import com.warage.server.model.PlayerProfile;
import com.warage.server.repository.AchievementsRepository;
import com.warage.server.repository.PlayerAchievementRepository;
import com.warage.server.service.PlayerProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerProfileController {

    private final PlayerProfileService playerProfileService;

    public PlayerProfileController(PlayerProfileService playerProfileService, AchievementsRepository achievementsRepository, PlayerAchievementRepository playerAchievementRepository) {
        this.playerProfileService = playerProfileService;
    }


    @PostMapping("/register")
    public ResponseEntity<PlayerProfile> registerPlayer(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        String email = request.get("email");

        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }

        try {
            PlayerProfile newProfile = playerProfileService.registerPlayer(username, password, email);
            return new ResponseEntity<>(newProfile, HttpStatus.CREATED); // 201 Created
        } catch (IllegalArgumentException e) {
            // Возвращаем 409 Conflict, если пользователь/email уже существует
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<PlayerProfile> loginPlayer(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            PlayerProfile profile = playerProfileService.loginPlayer(username, password);
            return ResponseEntity.ok(profile); // 200 OK
        } catch (IllegalArgumentException e) {
            // Например, 401 Unauthorized если пароль неверный, или 404 Not Found если пользователь не найден
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}