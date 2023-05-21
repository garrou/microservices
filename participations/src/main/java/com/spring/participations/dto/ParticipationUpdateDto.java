package com.spring.participations.dto;

import com.spring.participations.entities.Presence;
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
public class ParticipationUpdateDto {

    @NotBlank(message = "Id can't be empty")
    private String id;

    @NotBlank(message = "CourseId can't be empty")
    private String courseId;

    @Size(max = 500, message = "Maximum size is {max}")
    private List<Presence> presences;
}
