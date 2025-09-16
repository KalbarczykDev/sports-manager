package dev.kalbarczyk.sportsmanager.user.model;

import dev.kalbarczyk.sportsmanager.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class representing {@link User} entity.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public final class User extends BaseEntity {

    @Column(nullable = false, unique = true, name = "email")
    private String email;
    @ToString.Exclude
    @Column(nullable = false, name = "password")
    private String password;

    @Column(name = "is_admin")
    private boolean isAdmin;

    public User(final String email, final String password, final boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }
}
