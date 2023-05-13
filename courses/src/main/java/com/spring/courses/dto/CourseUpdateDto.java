package com.spring.courses.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.courses.validators.Uuid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseUpdateDto {

    @NotBlank(message = "Id can't be empty")
    private String id;

    @NotBlank(message = "Title can't be empty")
    private String title;

    @Min(value = 0, message = "The minimum level is {value}")
    @Max(value = 5, message = "The maximum level is {value}")
    private int level;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date timeSlot;

    @DecimalMin(value = "1.0", message = "Minimum duration is {value}")
    private double duration;

    @NotBlank(message = "Location cant' be empty")
    private String location;

    @Uuid(message = "Teacher id is invalid")
    private UUID teacherId;

    @Size(max = 500)
    private List<UUID> students;

    @Min(value = 1, message = "Minimum value is {value}")
    @Max(value = 500, message = "Maximum value is {value}")
    private int nbStudentsMax;
}
