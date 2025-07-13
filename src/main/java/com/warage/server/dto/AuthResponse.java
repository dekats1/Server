// src/main/java/com/warage/server/dto/AuthResponse.java
package com.warage.server.dto;

import com.warage.server.model.PlayerProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String jwtToken;
    private String refreshToken; // Добавляем refresh token
    private PlayerProfile playerProfile;
}