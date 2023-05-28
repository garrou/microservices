package com.spring.users.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.users.dto.LoginDto;
import com.spring.users.dto.PersonCreationDto;
import com.spring.users.dto.PersonUpdateDto;
import com.spring.users.entities.Person;
import com.spring.users.enums.Role;
import com.spring.users.exceptions.PersonNotFoundException;
import com.spring.users.exceptions.WrongAuthentificationException;
import com.spring.users.services.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static com.spring.users.utils.Helper.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;

    private final ModelMapper modelMapper = new ModelMapper();

    private PersonCreationDto personCreationDto;

    private PersonUpdateDto personUpdateDto;

    public static final Person PERSON = new Person(
            UUID.randomUUID(),
            "test-firstname",
            "test-lastname",
            "test-pseudo",
            "test-email@gmail.com",
            "test-address",
            3,
            Role.TEACHER,
            "test-test"
    );

    @Before
    public void setUp() {
        personCreationDto = modelMapper.map(PERSON, PersonCreationDto.class);
        personUpdateDto = modelMapper.map(PERSON, PersonUpdateDto.class);
    }

    @Test
    public void getAllPersonsTest() throws Exception {

        when(personService.getPersons(null, null, null)).thenReturn(List.of());

        mvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getOnePersonTest() throws Exception {

        when(personService.getPerson(PERSON.getId())).thenReturn(PERSON);

        mvc.perform(get("/api/users/{id}", PERSON.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(8)))
                .andExpect(jsonPath("$.id").value(PERSON.getId().toString()))
                .andExpect(jsonPath("$.firstname").value(PERSON.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(PERSON.getLastname()))
                .andExpect(jsonPath("$.pseudo").value(PERSON.getPseudo()))
                .andExpect(jsonPath("$.email").value(PERSON.getEmail()))
                .andExpect(jsonPath("$.address").value(PERSON.getAddress()))
                .andExpect(jsonPath("$.level").value(PERSON.getLevel()))
                .andExpect(jsonPath("$.role").value(PERSON.getRole().toString()));
    }

    @Test
    public void getOnePersonNotFoundTest() throws Exception {

        when(personService.getPerson(PERSON.getId())).thenThrow(PersonNotFoundException.class);

        mvc.perform(get("/api/users/{id}", PERSON.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void createPersonCreatedTest() throws Exception {

        when(personService.createPerson(ArgumentMatchers.any(PersonCreationDto.class))).thenReturn(PERSON);

        mvc.perform(post("/api/users")
                        .content(asJsonString(personCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(String.format("http://**/users/%s", PERSON.getId())))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(8)))
                .andExpect(jsonPath("$.id").value(PERSON.getId().toString()))
                .andExpect(jsonPath("$.firstname").value(personCreationDto.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(personCreationDto.getLastname()))
                .andExpect(jsonPath("$.pseudo").value(personCreationDto.getPseudo()))
                .andExpect(jsonPath("$.email").value(personCreationDto.getEmail()))
                .andExpect(jsonPath("$.address").value(personCreationDto.getAddress()))
                .andExpect(jsonPath("$.level").value(personCreationDto.getLevel()))
                .andExpect(jsonPath("$.role").value(personCreationDto.getRole()));
    }

    @Test
    public void createPersonEmailBadRequestTest() throws Exception {

        personCreationDto.setEmail("test-email");

        when(personService.createPerson(ArgumentMatchers.any(PersonCreationDto.class))).thenReturn(PERSON);

        mvc.perform(post("/api/users")
                        .content(asJsonString(personCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(
                        jsonPath("$.email")
                                .value(String.format("Email '%s' is invalid", personCreationDto.getEmail()))
                );
    }

    @Test
    public void createPersonRoleBadRequestTest() throws Exception {

        personCreationDto.setRole("UNKNOWN");

        when(personService.createPerson(ArgumentMatchers.any(PersonCreationDto.class))).thenReturn(PERSON);

        mvc.perform(post("/api/users")
                        .content(asJsonString(personCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(
                        jsonPath("$.role")
                                .value(String.format("Role '%s' is invalid", personCreationDto.getRole()))
                );
    }

    @Test
    public void createPersonBadRequestTest() throws Exception {

        personCreationDto = new PersonCreationDto();

        when(personService.createPerson(ArgumentMatchers.any(PersonCreationDto.class))).thenReturn(PERSON);

        mvc.perform(post("/api/users")
                        .content(asJsonString(personCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(7)))
                .andExpect(jsonPath("$.firstname").value("Firstname can't be empty"))
                .andExpect(jsonPath("$.lastname").value("Lastname can't be empty"))
                .andExpect(jsonPath("$.pseudo").value("Pseudo can't be empty"))
                .andExpect(jsonPath("$.email").value("Email can't be empty"))
                .andExpect(jsonPath("$.address").value("Address can't be empty"))
                .andExpect(jsonPath("$.role").value("Role '' is invalid"))
                .andExpect(jsonPath("$.password").value("Password can't be empty"));
    }

    @Test
    public void updatePersonOkTest() throws Exception {

        when(
                personService.updatePerson(
                        ArgumentMatchers.eq(personUpdateDto.getId()),
                        ArgumentMatchers.any(PersonUpdateDto.class)
                )
        ).thenReturn(PERSON);

        mvc.perform(put("/api/users/{id}", personUpdateDto.getId())
                        .content(asJsonString(personUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(8)))
                .andExpect(jsonPath("$.id").value(personUpdateDto.getId().toString()))
                .andExpect(jsonPath("$.firstname").value(personUpdateDto.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(personUpdateDto.getLastname()))
                .andExpect(jsonPath("$.pseudo").value(personUpdateDto.getPseudo()))
                .andExpect(jsonPath("$.email").value(personUpdateDto.getEmail()))
                .andExpect(jsonPath("$.address").value(personUpdateDto.getAddress()))
                .andExpect(jsonPath("$.level").value(personUpdateDto.getLevel()))
                .andExpect(jsonPath("$.role").value(personUpdateDto.getRole()));
    }

    @Test
    public void updatePersonBadRequestTest() throws Exception {

        personUpdateDto.setFirstname("");
        personUpdateDto.setLastname("");
        personUpdateDto.setLevel(9);
        personUpdateDto.setAddress("");
        personUpdateDto.setPseudo("");
        personUpdateDto.setEmail("");
        personUpdateDto.setPassword("");
        personUpdateDto.setRole("UNKNOWN");

        when(personService.updatePerson(personUpdateDto.getId(), personUpdateDto)).thenReturn(PERSON);

        mvc.perform(put("/api/users/{id}", personUpdateDto.getId())
                        .content(asJsonString(personUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(8)))
                .andExpect(jsonPath("$.firstname").value("Firstname can't be empty"))
                .andExpect(jsonPath("$.lastname").value("Lastname can't be empty"))
                .andExpect(jsonPath("$.pseudo").value("Pseudo can't be empty"))
                .andExpect(jsonPath("$.email").value("Email can't be empty"))
                .andExpect(jsonPath("$.level").value("Maximum level is 5"))
                .andExpect(jsonPath("$.address").value("Address can't be empty"))
                .andExpect(jsonPath("$.role")
                        .value(
                                String.format("Role '%s' is invalid", personUpdateDto.getRole())
                        )
                )
                .andExpect(jsonPath("$.password").value("Password can't be empty"));
    }

    // TODO: Pseudo already exists on post and put
}
