package com.warage.server.controller;

import com.warage.server.model.Level;
import com.warage.server.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/levels")
public class LevelController {

    private final LevelService levelService;

    @Autowired
    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @GetMapping
    public ResponseEntity<List<Level>> getAllLevels() {
        List<Level> levels = levelService.getAllLevels();
        return ResponseEntity.ok(levels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Level> getLevelById(@PathVariable Integer id) {
        return levelService.getLevelById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Level> createLevel(@RequestBody Level level) {
        Level createdLevel = levelService.createLevel(level);
        return new ResponseEntity<>(createdLevel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Level> updateLevel(@PathVariable Integer id, @RequestBody Level levelDetails) {
        Level updatedLevel = levelService.updateLevel(id, levelDetails);
        if (updatedLevel != null) {
            return ResponseEntity.ok(updatedLevel);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLevel(@PathVariable Integer id) {
        if (levelService.deleteLevel(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }
}