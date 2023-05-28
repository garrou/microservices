package com.spring.users.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDto {

    @NotBlank(message = "Pseudo can't be empty")
    private String pseudo;

    @NotBlank(message = "Password can't be empty")
    private String password;
}
