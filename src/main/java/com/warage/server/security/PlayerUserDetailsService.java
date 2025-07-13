// src/main/java/com/warage/server/security/PlayerUserDetailsService.java
package com.warage.server.security;

import com.warage.server.repository.PlayerProfileRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PlayerUserDetailsService implements UserDetailsService {

    private final PlayerProfileRepository playerProfileRepository;

    public PlayerUserDetailsService(PlayerProfileRepository playerProfileRepository) {
        this.playerProfileRepository = playerProfileRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return playerProfileRepository.findByUsername(username)
                .map(PlayerDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}