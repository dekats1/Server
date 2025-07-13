package com.warage.server.service;

import com.warage.server.model.Version;
import com.warage.server.repository.VersionRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired; // Добавлен импорт

import java.util.List;
import java.util.Optional;

@Service
public class VersionService {

    private final VersionRepository versionRepository; // Изменен на final

    @Autowired
    public VersionService(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    public List<Version> getAllVersions() {
        return versionRepository.findAll();
    }

    public Optional<Version> getVersion(Integer id) {
        return versionRepository.findById(id);
    }

    public Version getLatestVersion() {
        return versionRepository.findTopByOrderByDataVersionDesc()
                .orElse(null); // Возвращаем null, если версий нет
    }
}