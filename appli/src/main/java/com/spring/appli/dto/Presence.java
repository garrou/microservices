package com.spring.appli.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Presence {

    private String id;

    private String badgeId;

    private boolean presence;

    private Double note;
}
