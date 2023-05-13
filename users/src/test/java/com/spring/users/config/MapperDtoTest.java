package com.spring.users.config;

import com.spring.users.configs.MapperDto;
import com.spring.users.dto.PersonCreationDto;
import com.spring.users.dto.PersonUpdateDto;
import com.spring.users.entities.Person;
import com.spring.users.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapperDtoTest {

    private MapperDto mapperDto;

    @BeforeEach
    public void setup() {
        mapperDto = new MapperDto();
    }

    @Test
    public void mappingCreationDtoTest() {
        PersonCreationDto personCreationDto = new PersonCreationDto(
                "Toto",
                "Tata",
                "tt",
                "tt@gmail.com",
                "9 speed road",
                4,
                "TEACHER",
                "test"
        );
        Person person = mapperDto.modelMapper().map(personCreationDto, Person.class);

        assertEquals(personCreationDto.getFirstname(), person.getFirstname(), "Firstname must be equal");
        assertEquals(personCreationDto.getLastname(), person.getLastname(), "Lastname must be equal");
        assertEquals(personCreationDto.getPseudo(), person.getPseudo(), "Pseudo must be equal");
        assertEquals(personCreationDto.getAddress(), person.getAddress(), "Address must be equal");
        assertEquals(personCreationDto.getLevel(), person.getLevel(), "Level must be equal");
        assertEquals(Role.TEACHER, person.getRole(), "Role must be equal");
        assertEquals(personCreationDto.getPassword(), person.getPassword(), "Password must be equal");
    }

    @Test
    public void mappingUpdateDtoTest() {
        PersonUpdateDto personUpdateDto = new PersonUpdateDto(
                UUID.randomUUID(),
                "Toto",
                "Tata",
                "tt",
                "tt@gmail.com",
                "9 speed road",
                4,
                "TEACHER",
                "test"
        );
        Person person = mapperDto.modelMapper().map(personUpdateDto, Person.class);

        assertEquals(personUpdateDto.getId(), person.getId(), "Id must be equal");
        assertEquals(personUpdateDto.getFirstname(), person.getFirstname(), "Firstname must be equal");
        assertEquals(personUpdateDto.getLastname(), person.getLastname(), "Lastname must be equal");
        assertEquals(personUpdateDto.getPseudo(), person.getPseudo(), "Pseudo must be equal");
        assertEquals(personUpdateDto.getAddress(), person.getAddress(), "Address must be equal");
        assertEquals(personUpdateDto.getLevel(), person.getLevel(), "Level must be equal");
        assertEquals(Role.TEACHER, person.getRole(), "Role must be equal");
        assertEquals(personUpdateDto.getPassword(), person.getPassword(), "Password must be equal");
    }
}
