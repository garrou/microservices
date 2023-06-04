package com.spring.appli.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "CourseId can't be empty")
    private String courseId;

    @Size(max = 500, message = "Maximum size is {max}")
    private List<Presence> presences;
}
