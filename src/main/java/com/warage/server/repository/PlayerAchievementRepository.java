package com.warage.server.repository;

import com.warage.server.model.PlayerAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerAchievementRepository extends JpaRepository<PlayerAchievement, Long> {

}
