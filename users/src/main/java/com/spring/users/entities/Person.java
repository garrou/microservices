package com.spring.users.entities;

import com.spring.users.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstname;

    private String lastname;

    @Column(unique = true)
    private String pseudo;

    private String email;

    private String address;

    private int level;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String password;
}
