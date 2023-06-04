package com.spring.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseParticipationDto {
    private String id;

    private String title;

    private int level;

    private Date timeSlot;

    private double duration;

    private String location;

    private UUID teacherId;

    private List<UUID> students;

    private int nbMaxStudents;

    private List<Participation> participations;
}
