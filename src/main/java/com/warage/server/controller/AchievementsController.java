package com.warage.server.controller;

import com.warage.server.model.Achievement;
import com.warage.server.service.AchievementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achievements")
public class AchievementsController {
      private final AchievementsService achievementsService;

      @Autowired
      public AchievementsController(AchievementsService achievementsService) {
          this.achievementsService = achievementsService;
      }


      @GetMapping
      public ResponseEntity<List<Achievement>> getAchievements() {
            List<Achievement> achievements = achievementsService.getAllAchievements();
            return ResponseEntity.ok(achievements);
      }

      @GetMapping("/{id}")
      public ResponseEntity<Achievement> getAchievementById(@PathVariable Integer id) {
            return achievementsService.getAchievementById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
      }

      @PostMapping
      public ResponseEntity<Achievement> createAchievement(@RequestBody Achievement achievement) {
            Achievement newAchievement = achievementsService.createAchievement(achievement);
            return new ResponseEntity<>(newAchievement, HttpStatus.CREATED);
      }

      @PutMapping("/{id}")
      public ResponseEntity<Achievement> updateAchievement(@PathVariable Integer id, @RequestBody Achievement achievement) {
            Achievement updatedAchievement = achievementsService.updateAchievement(id, achievement);
            if(updatedAchievement != null) {
                  return ResponseEntity.ok(updatedAchievement);
            }
            return ResponseEntity.notFound().build();
      }

      @DeleteMapping("/{id}")
      public ResponseEntity<Void> deleteAchievement(@PathVariable Integer id) {
            if(achievementsService.deleteAchievement(id)) {
                  return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
      }
}
