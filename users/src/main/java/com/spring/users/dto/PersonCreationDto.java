package com.spring.users.dto;

import com.spring.users.enums.Role;
import com.spring.users.validators.ValueOfEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Email can't be empty")
    @Email(message = "Email '${validatedValue}' is invalid")
    private String email;

    @NotBlank(message = "Address can't be empty")
    private String address;

    @Min(value = 0, message = "Minimum level is {value}")
    @Max(value = 5, message = "Maximum level is {value}")
    private int level;

    @ValueOfEnum(enumClass = Role.class, message = "Role '${validatedValue}' is invalid")
    private String role;

    @NotBlank(message = "Password can't be empty")
    private String password;
}
