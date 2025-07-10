package com.warage.server.service;

import com.warage.server.model.Tower;
import com.warage.server.model.TowerUpgrade;
import com.warage.server.repository.TowerUpgradeRepository;
import com.warage.server.repository.TowerRepository; // Добавляем для проверки Tower
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TowerUpgradeService {

    private final TowerUpgradeRepository towerUpgradeRepository;
    private final TowerRepository towerRepository;

    @Autowired
    public TowerUpgradeService(TowerUpgradeRepository towerUpgradeRepository, TowerRepository towerRepository) {
        this.towerUpgradeRepository = towerUpgradeRepository;
        this.towerRepository = towerRepository;
    }

    public List<TowerUpgrade> getAllTowerUpgrades() {
        return towerUpgradeRepository.findAll();
    }

    public Optional<TowerUpgrade> getTowerUpgradeById(Integer id) {
        return towerUpgradeRepository.findById(id);
    }

    @Transactional
    public TowerUpgrade createTowerUpgrade(TowerUpgrade towerUpgrade) {
        Integer towerId = towerUpgrade.getTower().getTowerID();
        Tower tower = towerRepository.findById(towerId)
                .orElseThrow(() -> new RuntimeException("Tower with ID " + towerId + " not found."));

        towerUpgrade.setTower(tower);
        return towerUpgradeRepository.save(towerUpgrade);
    }

    @Transactional
    public TowerUpgrade updateTowerUpgrade(Integer id, TowerUpgrade towerUpgradeDetails) {
        return towerUpgradeRepository.findById(id)
                .map(existingUpgrade -> {
                    existingUpgrade.setUpgradePath(towerUpgradeDetails.getUpgradePath());
                    existingUpgrade.setUpgradeLevel(towerUpgradeDetails.getUpgradeLevel());
                    existingUpgrade.setCost(towerUpgradeDetails.getCost());
                    existingUpgrade.setDamage(towerUpgradeDetails.getDamage());
                    existingUpgrade.setRange(towerUpgradeDetails.getRange());
                    existingUpgrade.setAttackSpeed(towerUpgradeDetails.getAttackSpeed());
                    existingUpgrade.setSpecialEffectDescription(towerUpgradeDetails.getSpecialEffectDescription());
                    existingUpgrade.setCanDetectInvisible(towerUpgradeDetails.getCanDetectInvisible());

                    if (towerUpgradeDetails.getTower() != null &&
                            !existingUpgrade.getTower().getTowerID().equals(towerUpgradeDetails.getTower().getTowerID())) {
                        Integer newTowerId = towerUpgradeDetails.getTower().getTowerID();
                        Tower newTower = towerRepository.findById(newTowerId)
                                .orElseThrow(() -> new RuntimeException("Tower with ID " + newTowerId + " not found."));
                        existingUpgrade.setTower(newTower);
                    }

                    return towerUpgradeRepository.save(existingUpgrade);
                })
                .orElse(null);
    }

    public boolean deleteTowerUpgrade(Integer id) {
        if (towerUpgradeRepository.existsById(id)) {
            towerUpgradeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<TowerUpgrade> getTowerUpgradesByTowerId(Integer towerId) {
        Tower tower = towerRepository.findById(towerId)
                .orElseThrow(() -> new RuntimeException("Tower with ID " + towerId + " not found."));
        return towerUpgradeRepository.findByTower(tower);
    }

    public List<TowerUpgrade> getTowerUpgradesByTowerIdAndPath(Integer towerId, Integer upgradePath) {
        Tower tower = towerRepository.findById(towerId)
                .orElseThrow(() -> new RuntimeException("Tower with ID " + towerId + " not found."));
        return towerUpgradeRepository.findByTowerAndUpgradePath(tower, upgradePath);
    }

    public Optional<TowerUpgrade> getTowerUpgradeByTowerIdAndPathAndLevel(Integer towerId, Integer upgradePath, Integer upgradeLevel) {
        Tower tower = towerRepository.findById(towerId)
                .orElseThrow(() -> new RuntimeException("Tower with ID " + towerId + " not found."));
        return towerUpgradeRepository.findByTowerAndUpgradePathAndUpgradeLevel(tower, upgradePath, upgradeLevel);
    }
}