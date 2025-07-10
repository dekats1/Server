package com.warage.server.controller;

import com.warage.server.model.PlayerProgress;
import com.warage.server.service.PlayerProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player-progress")
public class PlayerProgressController {

    private final PlayerProgressService playerProgressService;

    @Autowired
    public PlayerProgressController(PlayerProgressService playerProgressService) {
        this.playerProgressService = playerProgressService;
    }

    @GetMapping
    public ResponseEntity<List<PlayerProgress>> getAllPlayerProgress() {
        List<PlayerProgress> progressList = playerProgressService.getAllPlayerProgress();
        return ResponseEntity.ok(progressList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerProgress> getPlayerProgressById(@PathVariable Long id) {
        return playerProgressService.getPlayerProgressById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<PlayerProgress>> getPlayerProgressByPlayerId(@PathVariable Long playerId) {
        try {
            List<PlayerProgress> progressList = playerProgressService.getPlayerProgressByPlayerId(playerId);
            return ResponseEntity.ok(progressList);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 если игрок не найден
        }
    }

    @GetMapping("/player/{playerId}/level/{levelId}")
    public ResponseEntity<PlayerProgress> getPlayerProgressByPlayerIdAndLevelId(@PathVariable Long playerId, @PathVariable Integer levelId) {
        try {
            return playerProgressService.getPlayerProgressByPlayerIdAndLevelId(playerId, levelId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 если игрок или уровень не найден
        }
    }

    @PostMapping
    public ResponseEntity<PlayerProgress> createPlayerProgress(@RequestBody PlayerProgress playerProgress) {
        try {
            PlayerProgress createdProgress = playerProgressService.createPlayerProgress(playerProgress);
            return new ResponseEntity<>(createdProgress, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Если игрок или уровень не найдены
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerProgress> updatePlayerProgress(@PathVariable Long id, @RequestBody PlayerProgress playerProgressDetails) {
        try {
            PlayerProgress updatedProgress = playerProgressService.updatePlayerProgress(id, playerProgressDetails);
            if (updatedProgress != null) {
                return ResponseEntity.ok(updatedProgress);
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            // Если связанный игрок или уровень не найдены при обновлении
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayerProgress(@PathVariable Long id) {
        if (playerProgressService.deletePlayerProgress(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }
}