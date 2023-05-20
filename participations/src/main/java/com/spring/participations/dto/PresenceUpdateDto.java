package com.spring.participations.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PresenceUpdateDto {

    @NotBlank(message = "Id can't be empty")
    private String id;

    @NotBlank(message = "badgeId can't be empty")
    private String badgeId;

    private boolean presence;

    private Date date;

    @DecimalMin(value = "0.0", message = "Minimum note is {value}")
    @DecimalMax(value = "20.0", message = "Maximum note is {value}")
    private Double note;


}