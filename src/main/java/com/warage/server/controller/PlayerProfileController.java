package com.warage.server.controller;

import com.warage.server.model.PlayerProfile;
import com.warage.server.service.PlayerProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController // Указывает, что это REST-контроллер
@RequestMapping("/api/v1/players") // Базовый URL для всех эндпоинтов в этом контроллере
public class PlayerProfileController {

    private final PlayerProfileService playerProfileService;

    public PlayerProfileController(PlayerProfileService playerProfileService) {
        this.playerProfileService = playerProfileService;
    }

    // Эндпоинт для получения профиля игрока по ID
    // GET /api/v1/players/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PlayerProfile> getPlayerProfileById(@PathVariable Long id) {
        return playerProfileService.getPlayerProfileById(id)
                .map(ResponseEntity::ok) // Если найден, возвращаем 200 OK и профиль
                .orElse(ResponseEntity.notFound().build()); // Если не найден, возвращаем 404 Not Found
    }

    // Эндпоинт для получения профиля игрока по имени пользователя
    // GET /api/v1/players/username/{username}
    @GetMapping("/username/{username}")
    public ResponseEntity<PlayerProfile> getPlayerProfileByUsername(@PathVariable String username) {
        return playerProfileService.getPlayerProfileByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Эндпоинт для получения списка всех игроков (возможно, для админ-панели или таблицы лидеров)
    // GET /api/v1/players
    @GetMapping
    public ResponseEntity<List<PlayerProfile>> getAllPlayerProfiles() {
        List<PlayerProfile> players = playerProfileService.getAllPlayerProfiles();
        return ResponseEntity.ok(players);
    }

    // Эндпоинт для создания нового профиля игрока
    // POST /api/v1/players/register
    @PostMapping("/register")
    public ResponseEntity<PlayerProfile> registerPlayer(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }
        try {
            PlayerProfile newProfile = playerProfileService.createPlayerProfile(username);
            return new ResponseEntity<>(newProfile, HttpStatus.CREATED); // 201 Created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409 Conflict (пользователь уже существует)
        }
    }

    // Эндпоинт для логина игрока (или создания, если нет)
    // POST /api/v1/players/login
    @PostMapping("/login")
    public ResponseEntity<PlayerProfile> loginPlayer(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        PlayerProfile profile = playerProfileService.loginPlayer(username);
        return ResponseEntity.ok(profile); // 200 OK
    }

    // Эндпоинт для обновления данных игрока
    // PUT /api/v1/players/{id}
    @PutMapping("/{id}")
    public ResponseEntity<PlayerProfile> updatePlayerProfile(@PathVariable Long id, @RequestBody PlayerProfile playerProfile) {
        try {
            PlayerProfile updated = playerProfileService.updatePlayerProfile(id, playerProfile);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Эндпоинт для удаления профиля игрока
    // DELETE /api/v1/players/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayerProfile(@PathVariable Long id) {
        try {
            playerProfileService.deletePlayerProfile(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Или другой статус, если ID не найден
        }
    }
}