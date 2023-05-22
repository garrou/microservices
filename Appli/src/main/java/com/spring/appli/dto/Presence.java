package com.spring.appli.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
