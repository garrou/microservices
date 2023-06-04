package com.spring.competitions.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.competitions.validators.Uuid;
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
public class CompetitionUpdateDto {

    @NotBlank(message = "Id can't be empty")
    private String id;

    @NotBlank(message = "Title can't be empty")
    private String title;

    @Min(value = 0, message = "Minimum level is {value}")
    @Max(value = 5, message = "Maximum level is {value}")
    private int level;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date timeSlot;

    @DecimalMin(value = "1.0", message = "Minimum duration is {value}")
    private double duration;

    @NotBlank(message = "Location can't be empty")
    private String location;

    @Uuid(message = "Teacher id is invalid")
    private UUID teacherId;

    @Size(max = 500, message = "Maximum number of students is {max}")
    private List<UUID> students;

    @Min(value = 1, message = "Minimum value is {value}")
    @Max(value = 500, message = "Maximum value is {value}")
    private int nbMaxStudents;
}
