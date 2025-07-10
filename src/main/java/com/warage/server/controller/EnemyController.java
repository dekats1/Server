package com.warage.server.controller;

import com.warage.server.model.Enemy;
import com.warage.server.service.EnemyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enemies")
public class EnemyController {

    private final EnemyService enemyService;

    @Autowired
    public EnemyController(EnemyService enemyService) {
        this.enemyService = enemyService;
    }


    @GetMapping
    public ResponseEntity<List<Enemy>> getAllEnemies() {
        List<Enemy> enemies = enemyService.getAllEnemies();
        return ResponseEntity.ok(enemies);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Enemy> getEnemyById(@PathVariable Integer id) {
        return enemyService.getEnemyById(id).map(ResponseEntity::ok) // Если враг найден, вернуть 200 OK с врагом
                .orElse(ResponseEntity.notFound().build()); // Иначе вернуть 404 Not Found
    }


    @PostMapping
    public ResponseEntity<Enemy> createEnemy(@RequestBody Enemy enemy) {
        Enemy createdEnemy = enemyService.createEnemy(enemy);
        return new ResponseEntity<>(createdEnemy, HttpStatus.CREATED); // Вернуть 201 Created
    }


    @PutMapping("/{id}")
    public ResponseEntity<Enemy> updateEnemy(@PathVariable Integer id, @RequestBody Enemy enemyDetails) {
        Enemy updatedEnemy = enemyService.updateEnemy(id, enemyDetails);
        if (updatedEnemy != null) {
            return ResponseEntity.ok(updatedEnemy); // Вернуть 200 OK с обновленным врагом
        }
        return ResponseEntity.notFound().build(); // Иначе вернуть 404 Not Found
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnemy(@PathVariable Integer id) {
        if (enemyService.deleteEnemy(id)) {
            return ResponseEntity.noContent().build(); // Вернуть 204 No Content
        }
        return ResponseEntity.notFound().build(); // Иначе вернуть 404 Not Found
    }
}