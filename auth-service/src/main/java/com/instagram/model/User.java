package com.instagram.model;

import lombok.*;

import javax.persistence.*;
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

    private boolean isDeleted;

    private String role;
}