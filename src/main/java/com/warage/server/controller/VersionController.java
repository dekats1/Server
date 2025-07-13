package com.warage.server.controller;

// import com.warage.server.model.Enemy; // Этот импорт, вероятно, не нужен здесь
import com.warage.server.model.Version;
import com.warage.server.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/version")
public class VersionController {
    private final VersionService versionService;

    @Autowired
    public VersionController(VersionService versionService) {
        this.versionService = versionService;
    }

    @GetMapping
    public ResponseEntity<List<Version>> getAllVersions() {
        List<Version> versions = versionService.getAllVersions();
        return ResponseEntity.ok(versions); // Возвращаем список всех версий
    }

    @GetMapping("/latest")
    public ResponseEntity<Version> getLatestVersion() {
        Version latestVersion = versionService.getLatestVersion();
        if (latestVersion != null) {
            return ResponseEntity.ok(latestVersion);
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404, если версий нет
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Version> getVersionById(@PathVariable Integer id) { // Если нужен поиск по ID
        Optional<Version> version = versionService.getVersion(id);
        return version.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}