package com.spring.competitions.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.competitions.dto.CompetitionCreationDto;
import com.spring.competitions.dto.CompetitionUpdateDto;
import com.spring.competitions.entities.Competition;
import com.spring.competitions.exceptions.CompetitionNotFoundException;
import com.spring.competitions.services.CompetitionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CompetitionController.class)
public class CompetitionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompetitionService competitionService;

    private ModelMapper modelMapper = new ModelMapper();

    private CompetitionCreationDto competitionCreationDto;

    private CompetitionUpdateDto competitionUpdateDto;

    private static final Competition COMPETITION = new Competition(
            "test",
            "Test competition",
            4,
            new Date(),
            90.0,
            "Test location",
            UUID.randomUUID(),
            List.of(),
            25
    );

    @Before
    public void setUp() {
        competitionCreationDto = modelMapper.map(COMPETITION, CompetitionCreationDto.class);
        competitionUpdateDto = modelMapper.map(COMPETITION, CompetitionUpdateDto.class);
    }

    @Test
    public void getAllCompetitionsTest() throws Exception {

        when(competitionService.getCompetitions(null, null, null)).thenReturn(List.of());

        mvc.perform(get("/api/competitions")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getOneCompetitionTest() throws Exception {

        when(competitionService.getCompetition("test")).thenReturn(COMPETITION);

        mvc.perform(get("/api/competitions/{id}", COMPETITION.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(9)))
                .andExpect(jsonPath("$.id").value(COMPETITION.getId()))
                .andExpect(jsonPath("$.title").value(COMPETITION.getTitle()))
                .andExpect(jsonPath("$.level").value(COMPETITION.getLevel()))
                .andExpect(jsonPath("$.timeSlot").exists())
                .andExpect(jsonPath("$.duration").value(COMPETITION.getDuration()))
                .andExpect(jsonPath("$.location").value(COMPETITION.getLocation()))
                .andExpect(jsonPath("$.teacherId").value(COMPETITION.getTeacherId().toString()))
                .andExpect(jsonPath("$.students").isArray())
                .andExpect(jsonPath("$.nbStudentsMax").value(COMPETITION.getNbStudentsMax()));
    }

    @Test
    public void getOneCompetitionNotFoundTest() throws Exception {

        when(competitionService.getCompetition(COMPETITION.getId())).thenThrow(CompetitionNotFoundException.class);

        mvc.perform(get("/api/competitions/{id}", COMPETITION.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void createCompetitionCreatedTest() throws Exception {

        when(competitionService.createCompetition(ArgumentMatchers.any(CompetitionCreationDto.class)))
                .thenReturn(COMPETITION);

        mvc.perform(post("/api/competitions")
                        .content(asJsonString(competitionCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(String.format("http://**/competitions/%s", COMPETITION.getId())))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(9)))
                .andExpect(jsonPath("$.id").value(COMPETITION.getId()))
                .andExpect(jsonPath("$.title").value(COMPETITION.getTitle()))
                .andExpect(jsonPath("$.level").value(COMPETITION.getLevel()))
                .andExpect(jsonPath("$.timeSlot").exists())
                .andExpect(jsonPath("$.duration").value(COMPETITION.getDuration()))
                .andExpect(jsonPath("$.location").value(COMPETITION.getLocation()))
                .andExpect(jsonPath("$.teacherId").value(COMPETITION.getTeacherId().toString()))
                .andExpect(jsonPath("$.students").isArray())
                .andExpect(jsonPath("$.nbStudentsMax").value(COMPETITION.getNbStudentsMax()));
    }

    @Test
    public void createCompetitionBadRequestTest() throws Exception {

        competitionCreationDto = new CompetitionCreationDto();

        when(competitionService.createCompetition(competitionCreationDto)).thenReturn(COMPETITION);

        mvc.perform(post("/api/competitions")
                        .content(asJsonString(competitionCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(5)))
                .andExpect(jsonPath("$.title").value("Title can't be empty"))
                .andExpect(jsonPath("$.duration").value("Minimum duration is 1.0"))
                .andExpect(jsonPath("$.location").value("Location can't be empty"))
                .andExpect(jsonPath("$.teacherId").value("Teacher id is invalid"))
                .andExpect(jsonPath("$.nbStudentsMax").value("Minimum value is 1"));
    }

    @Test
    public void updateCompetitionOkTest() throws Exception {

        when(
                competitionService.updateCompetition(
                        ArgumentMatchers.eq(competitionUpdateDto.getId()),
                        ArgumentMatchers.any(CompetitionUpdateDto.class)
                )
        ).thenReturn(COMPETITION);

        mvc.perform(put("/api/competitions/{id}", competitionUpdateDto.getId())
                        .content(asJsonString(competitionUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(9)))
                .andExpect(jsonPath("$.id").value(competitionUpdateDto.getId()))
                .andExpect(jsonPath("$.title").value(competitionUpdateDto.getTitle()))
                .andExpect(jsonPath("$.level").value(competitionUpdateDto.getLevel()))
                .andExpect(jsonPath("$.timeSlot").exists())
                .andExpect(jsonPath("$.duration").value(competitionUpdateDto.getDuration()))
                .andExpect(jsonPath("$.location").value(competitionUpdateDto.getLocation()))
                .andExpect(jsonPath("$.teacherId").value(competitionUpdateDto.getTeacherId().toString()))
                .andExpect(jsonPath("$.students").isArray())
                .andExpect(jsonPath("$.nbStudentsMax").value(competitionUpdateDto.getNbStudentsMax()));
    }

    @Test
    public void updateCompetitionBadRequestTest() throws Exception {

        competitionUpdateDto.setDuration(-17);
        competitionUpdateDto.setStudents(List.of(UUID.randomUUID()));
        competitionUpdateDto.setLevel(9);
        competitionUpdateDto.setTitle("");
        competitionUpdateDto.setLocation("");
        competitionUpdateDto.setNbStudentsMax(900);
        competitionUpdateDto.setTeacherId(null);

        when(competitionService.updateCompetition(competitionUpdateDto.getId(), competitionUpdateDto))
                .thenReturn(COMPETITION);

        mvc.perform(put("/api/competitions/{id}", competitionUpdateDto.getId())
                        .content(asJsonString(competitionUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(6)))
                .andExpect(jsonPath("$.title").value("Title can't be empty"))
                .andExpect(jsonPath("$.level").value("Maximum level is 5"))
                .andExpect(jsonPath("$.duration").value("Minimum duration is 1.0"))
                .andExpect(jsonPath("$.location").value("Location can't be empty"))
                .andExpect(jsonPath("$.teacherId").value("Teacher id is invalid"))
                .andExpect(jsonPath("$.nbStudentsMax").value("Maximum value is 500"));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
