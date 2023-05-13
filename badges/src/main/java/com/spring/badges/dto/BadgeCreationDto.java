package com.spring.badges.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BadgeCreationDto {

    private String id;
    @NotBlank(message = "idPerson can't be empty")
    private String idPerson;


}
