package com.warage.server.controller;

import com.warage.server.model.Tower;
import com.warage.server.service.TowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/towers")
public class TowerController {

    private final TowerService towerService;

    @Autowired
    public TowerController(TowerService towerService) {
        this.towerService = towerService;
    }

    @GetMapping
    public ResponseEntity<List<Tower>> getAllTowers() {
        List<Tower> towers = towerService.getAllTowers();
        return ResponseEntity.ok(towers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tower> getTowerById(@PathVariable Integer id) {
        return towerService.getTowerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tower> createTower(@RequestBody Tower tower) {
        Tower createdTower = towerService.createTower(tower);
        return new ResponseEntity<>(createdTower, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tower> updateTower(@PathVariable Integer id, @RequestBody Tower towerDetails) {
        Tower updatedTower = towerService.updateTower(id, towerDetails);
        if (updatedTower != null) {
            return ResponseEntity.ok(updatedTower);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTower(@PathVariable Integer id) {
        if (towerService.deleteTower(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }
}