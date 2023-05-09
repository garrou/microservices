package com.spring.users.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum Role {

    MEMBER("member"),

    SECRETARY("secretary"),

    TEACHER("teacher"),

    PRESIDENT("president");

    private final String name;

    @JsonCreator
    public static Role decode(final String role) {
        return Stream
                .of(Role.values())
                .filter(targetEnum -> targetEnum.getName().equals(role))
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
