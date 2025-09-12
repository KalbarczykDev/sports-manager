package dev.kalbarczyk.sportsmanager.user.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, unique = true, name = "email")
    private String email;
    @ToString.Exclude
    @Column(nullable = false, name = "password")
    private String password;

    @Column(name = "is_admin")
    private boolean isAdmin;

    public User(String username, String email, boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }
}
