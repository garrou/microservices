package com.spring.participations.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParticipationUpdateDto {

    @NotBlank
    private String id;

    @NotBlank
    private String courseId;
}
