package com.spring.participations.dto;

import com.spring.participations.entities.Presence;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParticipationCreationDto {

    @NotBlank
    private String courseId;

    private List<Presence> presenceList;
}
