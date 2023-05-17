package com.spring.participations.entities;

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
public class Presence {

    private String id;

    private String badgeId;

    private boolean presence;

    private Date date;

    private Double note;
}
