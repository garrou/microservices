package com.spring.users.dto;

import com.spring.users.enums.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonCreationDto {

    @NotBlank(message = "Firstname can't be empty")
    private String firstname;

    @NotBlank(message = "Lastname can't be empty")
    private String lastname;

    @NotBlank(message = "Pseudo can't be empty")
    private String pseudo;

    @Email(message = "The email '${validatedValue}' is not valid")
    private String email;

    @NotBlank(message = "Email can't be empty")
    private String address;

    @Min(value = 0, message = "The minimum level is {value}")
    @Max(value = 5, message = "The maximum level is {value}")
    private int level;

    // @NotBlank(message = "Role can't be empty")
    // private Role role;

    @NotBlank(message = "Password can't be empty")
    private String password;
}
