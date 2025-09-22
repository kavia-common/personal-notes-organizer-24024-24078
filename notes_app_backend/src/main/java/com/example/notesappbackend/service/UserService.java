package com.example.notesappbackend.service;

import com.example.notesappbackend.model.User;
import com.example.notesappbackend.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User service that implements UserDetailsService for Spring Security.
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository users;
    private final PasswordEncoder encoder;

    public UserService(UserRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    // PUBLIC_INTERFACE
    public User register(String username, String rawPassword) {
        if (users.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already taken");
        }
        User u = new User();
        u.setUsername(username);
        u.setPasswordHash(encoder.encode(rawPassword));
        return users.save(u);
    }

    // PUBLIC_INTERFACE
    public boolean verify(String username, String rawPassword) {
        return users.findByUsername(username)
                .map(u -> encoder.matches(rawPassword, u.getPasswordHash()))
                .orElse(false);
    }

    // PUBLIC_INTERFACE
    public User getByUsername(String username) {
        return users.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = getByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
