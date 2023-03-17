package com.spring.users.enums;

public enum Role {

    MEMBER("member"),

    SECRETARY("secretary"),

    TEACHER("teacher"),

    PRESIDENT("president");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
