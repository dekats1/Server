package com.warage.server.service;

import com.warage.server.model.Tower;
import com.warage.server.repository.TowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TowerService {

    private final TowerRepository towerRepository;

    @Autowired
    public TowerService(TowerRepository towerRepository) {
        this.towerRepository = towerRepository;
    }

    public List<Tower> getAllTowers() {
        return towerRepository.findAll();
    }

    public Optional<Tower> getTowerById(Integer id) {
        return towerRepository.findById(id);
    }

    public Tower createTower(Tower tower) {
        return towerRepository.save(tower);
    }

    public Tower updateTower(Integer id, Tower towerDetails) {
        return towerRepository.findById(id)
                .map(tower -> {
                    tower.setName(towerDetails.getName());
                    tower.setDescription(towerDetails.getDescription());
                    tower.setBaseCost(towerDetails.getBaseCost());
                    tower.setAssetPath(towerDetails.getAssetPath());
                    return towerRepository.save(tower);
                })
                .orElse(null); // Или выбросьте исключение, если башня не найдена
    }

    public boolean deleteTower(Integer id) {
        if (towerRepository.existsById(id)) {
            towerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}