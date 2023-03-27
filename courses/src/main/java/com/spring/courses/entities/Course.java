package com.spring.courses.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course {

    private String id;

    private String title;

    private int level;

    private Date timeSlot;

    private double duration;

    private String location;
}
