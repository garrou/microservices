package com.spring.users.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    MEMBER("member"),

    SECRETARY("secretary"),

    TEACHER("teacher"),

    PRESIDENT("president");

    private String name;
}
