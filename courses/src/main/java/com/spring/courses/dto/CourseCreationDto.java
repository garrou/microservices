package com.spring.courses.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseCreationDto {

    @NotBlank(message = "Title can't be empty")
    private String title;

    @Min(value = 0, message = "The minimum level is {value}")
    @Max(value = 5, message = "The maximum level is {value}")
    private int level;

    @NotEmpty
    private Date timeSlot;

    @DecimalMin(value = "0.0", message = "Minimum duration is {value}")
    private double duration;

    @NotBlank(message = "Location cant' be empty")
    private String location;
}
