// src/main/java/com/warage/server/repository/PlayerProgressRepository.java
package com.warage.server.repository;

import com.warage.server.model.PlayerProgress;
import com.warage.server.model.PlayerProfile;
import com.warage.server.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerProgressRepository extends JpaRepository<PlayerProgress, Long> {
    // Базовые CRUD-методы уже доступны.

    // Дополнительные методы, которые могут быть полезны:
    List<PlayerProgress> findByPlayer(PlayerProfile player);
    List<PlayerProgress> findByLevel(Level level);
    Optional<PlayerProgress> findByPlayerAndLevel(PlayerProfile player, Level level);
}