package dev.kalbarczyk.sportsmanager.user.init;

import dev.kalbarczyk.sportsmanager.common.init.EntitySeeder;
import dev.kalbarczyk.sportsmanager.user.model.User;
import dev.kalbarczyk.sportsmanager.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserSeeder extends EntitySeeder<User> implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected int getSeedCount() {
        return 1;
    }

    @Override
    protected long count() {
        return userRepository.count();
    }

    @Override
    protected void save(User entity) {
        userRepository.save(entity);
    }

    @Override
    protected User createRandomEntity() {
        return new User("admin@example.com", passwordEncoder.encode("admin"), true);
    }

    @Override
    public void run(String... args) {
        seed();
    }
}
