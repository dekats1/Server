package com.warage.server.service;

import com.warage.server.model.Enemy;
import com.warage.server.repository.EnemyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnemyService {

    private final EnemyRepository enemyRepository;

    @Autowired
    public EnemyService(EnemyRepository enemyRepository) {
        this.enemyRepository = enemyRepository;
    }

    public List<Enemy> getAllEnemies() {
        return enemyRepository.findAll();
    }

    public Optional<Enemy> getEnemyById(Integer id) {
        return enemyRepository.findById(id);
    }

    public Enemy createEnemy(Enemy enemy) {
        return enemyRepository.save(enemy);
    }

    public Enemy updateEnemy(Integer id, Enemy enemyDetails) {
        return enemyRepository.findById(id)
                .map(enemy -> {
                    enemy.setName(enemyDetails.getName());
                    enemy.setDescription(enemyDetails.getDescription());
                    enemy.setBaseHealth(enemyDetails.getBaseHealth());
                    enemy.setSpeed(enemyDetails.getSpeed());
                    enemy.setRewardCoins(enemyDetails.getRewardCoins());
                    enemy.setRewardExperience(enemyDetails.getRewardExperience());
                    enemy.setAssetPath(enemyDetails.getAssetPath());
                    enemy.setIsInvisible(enemyDetails.getIsInvisible());
                    return enemyRepository.save(enemy);
                })
                .orElse(null);
    }

    public boolean deleteEnemy(Integer id) {
        if (enemyRepository.existsById(id)) {
            enemyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}