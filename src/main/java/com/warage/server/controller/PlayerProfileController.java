// src/main/java/com/warage/server/controller/PlayerProfileController.java
package com.warage.server.controller;

import com.warage.server.dto.AuthRequest;
import com.warage.server.dto.AuthResponse;
import com.warage.server.model.PlayerProfile;
import com.warage.server.repository.AchievementsRepository;
import com.warage.server.repository.PlayerAchievementRepository;
import com.warage.server.security.JwtUtil;
import com.warage.server.security.PlayerUserDetailsService;
import com.warage.server.service.PlayerProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal; // <-- НОВЫЙ ИМПОРТ: Для получения информации о текущем пользователе
import java.util.Map;
import java.util.Optional; // <-- НОВЫЙ ИМПОРТ: Для работы с Optional

@RestController
@RequestMapping("/api/v1/players")
public class PlayerProfileController {

    private final PlayerProfileService playerProfileService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PlayerUserDetailsService userDetailsService;

    public PlayerProfileController(PlayerProfileService playerProfileService,
                                   AchievementsRepository achievementsRepository,
                                   PlayerAchievementRepository playerAchievementRepository,
                                   JwtUtil jwtUtil,
                                   AuthenticationManager authenticationManager,
                                   PlayerUserDetailsService userDetailsService) {
        this.playerProfileService = playerProfileService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        // Здесь вы можете инициализировать achievementsRepository и playerAchievementRepository, если они используются
        // this.achievementsRepository = achievementsRepository;
        // this.playerAchievementRepository = playerAchievementRepository;
    }


    @PostMapping("/register")
    public ResponseEntity<PlayerProfile> registerPlayer(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        String email = request.get("email");

        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }

        try {
            PlayerProfile newProfile = playerProfileService.registerPlayer(username, password, email);
            return new ResponseEntity<>(newProfile, HttpStatus.CREATED); // 201 Created
        } catch (IllegalArgumentException e) {
            // Возвращаем 409 Conflict, если пользователь/email уже существует
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginPlayer(@RequestBody AuthRequest authRequest) {
        try {
            // Аутентификация пользователя с помощью AuthenticationManager
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            // Если аутентификация прошла успешно, получаем UserDetails
            UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
            // Генерируем JWT токен
            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

            // Обновляем lastLogin в PlayerProfile
            // Важно: playerProfileService.loginPlayer должен просто обновить lastLogin и вернуть профиль,
            // без повторной проверки пароля, так как аутентификация уже прошла через AuthenticationManager.
            PlayerProfile playerProfile = playerProfileService.loginPlayer(authRequest.getUsername(), authRequest.getPassword());

            return ResponseEntity.ok(AuthResponse.builder()
                    .jwtToken(jwtToken)
                    .refreshToken(refreshToken)
                    .playerProfile(playerProfile)
                    .build());
        } catch (Exception e) {
            // Если аутентификация не удалась (неверный логин/пароль)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // Эндпоинт для получения профиля по ID (если он вам нужен)
    @GetMapping("/profile/{id}")
    public ResponseEntity<PlayerProfile> getPlayerProfileById(@PathVariable Long id) { // Переименовано для ясности
        return playerProfileService.getPlayerProfileById(id) // Убедитесь, что этот метод существует в PlayerProfileService
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // НОВЫЙ ЭНДПОИНТ: Для получения профиля текущего аутентифицированного пользователя
    @GetMapping("/profile") // Без {id} в пути
    public ResponseEntity<PlayerProfile> getAuthenticatedPlayerProfile(Principal principal) {
        // Principal предоставляет информацию об аутентифицированном пользователе
        if (principal == null) {
            // Этого не должно произойти, если фильтр безопасности работает правильно
            // и эндпоинт защищен.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String username = principal.getName(); // Получаем имя пользователя из Principal
        Optional<PlayerProfile> playerProfile = playerProfileService.getPlayerProfileByUsername(username); // Используем findByUsername

        if (playerProfile.isPresent()) {
            return ResponseEntity.ok(playerProfile.get());
        } else {
            // Это маловероятно, если пользователь только что был аутентифицирован,
            // но может случиться, если профиль был удален или возникла другая проблема.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}