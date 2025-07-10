package com.warage.server.repository;// src/main/java/com/yourgame/warage/repository/PlayerProfileRepository.java

import com.warage.server.model.PlayerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerProfileRepository extends JpaRepository<PlayerProfile, Long> {

    Optional<PlayerProfile> findByUsername(String username);
    Optional<PlayerProfile> findByEmail(String email); // Добавили метод для поиска по email
}