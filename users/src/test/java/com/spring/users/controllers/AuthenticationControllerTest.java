package com.spring.users.controllers;

import com.spring.users.dto.LoginDto;
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

import static com.spring.users.controllers.PersonControllerTest.PERSON;
import static com.spring.users.utils.Helper.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;

    private final ModelMapper modelMapper = new ModelMapper();

    private LoginDto loginDto;

    @Before
    public void setup() {
        loginDto = modelMapper.map(PERSON, LoginDto.class);
    }

    @Test
    public void loginTest() throws Exception {

        when(personService.login(ArgumentMatchers.any(LoginDto.class))).thenReturn(PERSON);

        mvc.perform(post("/api/auth/login")
                        .content(asJsonString(loginDto))
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
    public void loginTestWrongPassword() throws Exception {

        loginDto.setPassword("wrong");

        when(personService.login(ArgumentMatchers.any(LoginDto.class))).thenThrow(new WrongAuthentificationException());

        mvc.perform(post("/api/auth/login")
                        .content(asJsonString(loginDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }
}
