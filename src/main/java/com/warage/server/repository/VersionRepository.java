package com.warage.server.repository;

import com.warage.server.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VersionRepository extends JpaRepository<Version, Integer> {
    Optional<Version> findTopByOrderByDataVersionDesc();
}
