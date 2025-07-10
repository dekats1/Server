package com.warage.server.repository;

import com.warage.server.model.TowerUpgrade;
import com.warage.server.model.Tower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TowerUpgradeRepository extends JpaRepository<TowerUpgrade, Integer> {

    List<TowerUpgrade> findByTower(Tower tower);
    List<TowerUpgrade> findByTowerAndUpgradePath(Tower tower, Integer upgradePath);
    Optional<TowerUpgrade> findByTowerAndUpgradePathAndUpgradeLevel(Tower tower, Integer upgradePath, Integer upgradeLevel);
}