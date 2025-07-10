package com.warage.server.controller;

import com.warage.server.model.TowerUpgrade;
import com.warage.server.service.TowerUpgradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tower-upgrades")
public class TowerUpgradeController {

    private final TowerUpgradeService towerUpgradeService;

    @Autowired
    public TowerUpgradeController(TowerUpgradeService towerUpgradeService) {
        this.towerUpgradeService = towerUpgradeService;
    }

    @GetMapping
    public ResponseEntity<List<TowerUpgrade>> getAllTowerUpgrades() {
        List<TowerUpgrade> upgrades = towerUpgradeService.getAllTowerUpgrades();
        return ResponseEntity.ok(upgrades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TowerUpgrade> getTowerUpgradeById(@PathVariable Integer id) {
        return towerUpgradeService.getTowerUpgradeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tower/{towerId}")
    public ResponseEntity<List<TowerUpgrade>> getTowerUpgradesByTowerId(@PathVariable Integer towerId) {
        try {
            List<TowerUpgrade> upgrades = towerUpgradeService.getTowerUpgradesByTowerId(towerId);
            return ResponseEntity.ok(upgrades);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 если башня не найдена
        }
    }

    // GET /api/tower-upgrades/tower/{towerId}/path/{upgradePath}
    @GetMapping("/tower/{towerId}/path/{upgradePath}")
    public ResponseEntity<List<TowerUpgrade>> getTowerUpgradesByTowerIdAndPath(@PathVariable Integer towerId, @PathVariable Integer upgradePath) {
        try {
            List<TowerUpgrade> upgrades = towerUpgradeService.getTowerUpgradesByTowerIdAndPath(towerId, upgradePath);
            return ResponseEntity.ok(upgrades);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 если башня не найдена
        }
    }

    // GET /api/tower-upgrades/tower/{towerId}/path/{upgradePath}/level/{upgradeLevel}
    @GetMapping("/tower/{towerId}/path/{upgradePath}/level/{upgradeLevel}")
    public ResponseEntity<TowerUpgrade> getTowerUpgradeByTowerIdAndPathAndLevel(@PathVariable Integer towerId, @PathVariable Integer upgradePath, @PathVariable Integer upgradeLevel) {
        try {
            return towerUpgradeService.getTowerUpgradeByTowerIdAndPathAndLevel(towerId, upgradePath, upgradeLevel)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 если башня не найдена
        }
    }

    @PostMapping
    public ResponseEntity<TowerUpgrade> createTowerUpgrade(@RequestBody TowerUpgrade towerUpgrade) {
        try {
            TowerUpgrade createdUpgrade = towerUpgradeService.createTowerUpgrade(towerUpgrade);
            return new ResponseEntity<>(createdUpgrade, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Если связанная башня не найдена
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TowerUpgrade> updateTowerUpgrade(@PathVariable Integer id, @RequestBody TowerUpgrade towerUpgradeDetails) {
        try {
            TowerUpgrade updatedUpgrade = towerUpgradeService.updateTowerUpgrade(id, towerUpgradeDetails);
            if (updatedUpgrade != null) {
                return ResponseEntity.ok(updatedUpgrade);
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            // Если связанная башня не найдена при обновлении
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 Bad Request
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTowerUpgrade(@PathVariable Integer id) {
        if (towerUpgradeService.deleteTowerUpgrade(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }
}