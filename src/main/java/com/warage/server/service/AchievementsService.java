package com.warage.server.service;

import com.warage.server.model.Achievement;
import com.warage.server.repository.AchievementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AchievementsService {
    private final AchievementsRepository achievementsRepository;

    @Autowired
    public AchievementsService(AchievementsRepository achievementsRepository) {
        this.achievementsRepository = achievementsRepository;
    }

    public List<Achievement> getAllAchievements() {return achievementsRepository.findAll();}

    public Optional<Achievement> getAchievementById(Integer id) {return achievementsRepository.findById(id);}

    public Achievement createAchievement(Achievement achievement) {return achievementsRepository.save(achievement);}

    public Achievement updateAchievement(Integer id, Achievement achievement) {
        return achievementsRepository.findById(id).map(ach->{
            ach.setName(achievement.getName());
            ach.setDescription(achievement.getDescription());
            ach.setRewardMoney(achievement.getRewardMoney());
            ach.setRewardExperience(achievement.getRewardExperience());
            return achievementsRepository.save(ach);
        }).orElse(null);
    }

    public boolean deleteAchievement(Integer id) {
        if(achievementsRepository.existsById(id)) {
            achievementsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
