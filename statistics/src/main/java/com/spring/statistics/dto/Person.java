package com.spring.statistics.dto;


import com.spring.statistics.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {


    private UUID id;

    private String firstname;

    private String lastname;

    private String pseudo;

    private String email;

    private String address;

    private int level;

    private Role role;

    private String password;
}
