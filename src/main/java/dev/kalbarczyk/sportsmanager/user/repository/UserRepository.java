package dev.kalbarczyk.sportsmanager.user.repository;

import dev.kalbarczyk.sportsmanager.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for {@link User} entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their email address.
     *
     * @param email the email of the user
     * @return an {@link Optional} containing the user if found, otherwise empty
     */
    Optional<User> findByEmail(final String email);
}
