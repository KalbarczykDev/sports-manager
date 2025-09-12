package dev.kalbarczyk.sportsmanager.user.service;

import dev.kalbarczyk.sportsmanager.user.model.User;
import dev.kalbarczyk.sportsmanager.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String email, String rawPassword, boolean admin) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        var user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setAdmin(admin);
        return userRepository.save(user);
    }
}
