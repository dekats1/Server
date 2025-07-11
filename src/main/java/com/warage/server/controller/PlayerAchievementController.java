package com.warage.server.controller;

import com.warage.server.model.PlayerAchievement;
import com.warage.server.service.PlayerAchievementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/player-achievements")
public class PlayerAchievementController {
    private final PlayerAchievementService playerAchievementService;

    public PlayerAchievementController(PlayerAchievementService playerAchievementService){
        this.playerAchievementService = playerAchievementService;
    }

    @GetMapping
    public List<PlayerAchievement> getPlayerAchievements(){return playerAchievementService.getAllPlayerAchievements();}

    @GetMapping("/{id}")
    public Optional<PlayerAchievement> getPlayerAchievementById(@PathVariable Long id){return playerAchievementService.getPlayersAchievementById(id);}

    @PostMapping
    public PlayerAchievement createPlayerAchievement(@RequestBody PlayerAchievement pa) {
        return playerAchievementService.createAchievement(pa);
    }

    @DeleteMapping("/{id}")
    public void deleteAchievement(@PathVariable Long id) {
        playerAchievementService.deleteAchievement(id);
    }

    @PostMapping("/{id}/reward")
    public boolean rewardIfCompleted(@PathVariable Long id) {
        return playerAchievementService.rewardIfCompleted(id);
    }
}
