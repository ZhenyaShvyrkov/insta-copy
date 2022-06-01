package com.instagram.model.entity;

import com.instagram.model.Gender;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator", allocationSize = 1)
    private Long id;

    private UUID externalId;

    private String name;

    private String username;

    private String password;

    private String photoUrl;

    private String bio;

    private String website;

    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private boolean isDeleted;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "users_followers",
            joinColumns = @JoinColumn(name = "target_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id")
    )
    private final Set<User> followers = new HashSet<>();

    public void addFollower(User follower) {
        if (this.equals(follower)) {
            throw new UnsupportedOperationException("It is impossible to follow yourself");
        }

        followers.add(follower);
    }

    public void removeFollower(User follower) {
        followers.remove(follower);
    }
}