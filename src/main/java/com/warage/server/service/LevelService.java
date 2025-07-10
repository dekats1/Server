package com.warage.server.service;

import com.warage.server.model.Level;
import com.warage.server.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LevelService {

    private final LevelRepository levelRepository;

    @Autowired
    public LevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }

    public Optional<Level> getLevelById(Integer id) {
        return levelRepository.findById(id);
    }

    public Level createLevel(Level level) {
        return levelRepository.save(level);
    }

    public Level updateLevel(Integer id, Level levelDetails) {
        return levelRepository.findById(id)
                .map(level -> {
                    level.setName(levelDetails.getName());
                    level.setDescription(levelDetails.getDescription());
                    level.setStartingCoins(levelDetails.getStartingCoins());
                    level.setRewardMoney(levelDetails.getRewardMoney());
                    level.setMapDataPath(levelDetails.getMapDataPath());
                    level.setWaveConfigPath(levelDetails.getWaveConfigPath());
                    return levelRepository.save(level);
                })
                .orElse(null);
    }

    public boolean deleteLevel(Integer id) {
        if (levelRepository.existsById(id)) {
            levelRepository.deleteById(id);
            return true;
        }
        return false;
    }
}