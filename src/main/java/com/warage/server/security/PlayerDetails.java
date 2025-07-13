// src/main/java/com/warage/server/security/PlayerDetails.java
package com.warage.server.security;

import com.warage.server.model.PlayerProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class PlayerDetails implements UserDetails {

    private String username;
    private String password;

    public PlayerDetails(PlayerProfile playerProfile) {
        this.username = playerProfile.getUsername();
        this.password = playerProfile.getHashedPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Если у вас есть роли, верните их здесь. Пока нет, возвращаем пустую коллекцию.
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}