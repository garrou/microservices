package com.spring.appli.dto;

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
public class ParticipationUpdateDto {

    @NotBlank
    private String id;

    @NotBlank
    private String courseId;

    private List<Presence> presenceList;
}
