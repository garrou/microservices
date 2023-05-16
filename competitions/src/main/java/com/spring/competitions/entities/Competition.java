package com.spring.competitions.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Competition {

    private String id;

    private String title;

    private int level;

    private Date timeSlot;

    private double duration;

    private String location;

    private UUID teacherId;

    private List<UUID> students;

    private int nbStudentsMax;
}
