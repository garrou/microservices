package com.spring.participations.participations.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.participations.controllers.ParticipationController;
import com.spring.participations.dto.ParticipationCreationDto;
import com.spring.participations.dto.ParticipationUpdateDto;
import com.spring.participations.entities.Participation;
import com.spring.participations.entities.Presence;
import com.spring.participations.exceptions.ParticipationNotFoundException;
import com.spring.participations.services.ParticipationService;
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

import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ParticipationController.class)
public class ParticipationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ParticipationService participationService;

    private ModelMapper modelMapper = new ModelMapper();

    private ParticipationCreationDto participationCreationDto;

    private ParticipationUpdateDto participationUpdateDto;

    private static final Participation PARTICIPATION = new Participation(
            "test-id",
            "course-id",
            List.of()
    );

    @Before
    public void setUp() {
        participationCreationDto = modelMapper.map(PARTICIPATION, ParticipationCreationDto.class);
        participationUpdateDto = modelMapper.map(PARTICIPATION, ParticipationUpdateDto.class);
    }

    @Test
    public void getAllParticipationTest() throws Exception {

        when(participationService.getParticipations(null, null)).thenReturn(List.of(PARTICIPATION));

        mvc.perform(get("/api/participations")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getOneParticipationTest() throws Exception {

        when(participationService.getParticipation(PARTICIPATION.getId())).thenReturn(PARTICIPATION);

        mvc.perform(get("/api/participations/{id}", PARTICIPATION.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.id").value(PARTICIPATION.getId()))
                .andExpect(jsonPath("$.courseId").value(PARTICIPATION.getCourseId()))
                .andExpect(jsonPath("$.presences").isArray());
    }

    @Test
    public void getOneParticipationNotFoundTest() throws Exception {

        when(participationService.getParticipation(PARTICIPATION.getId())).thenThrow(ParticipationNotFoundException.class);

        mvc.perform(get("/api/participations/{id}", PARTICIPATION.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void createParticipationCreatedTest() throws Exception {

        when(participationService.createParticipation(ArgumentMatchers.any(ParticipationCreationDto.class)))
                .thenReturn(PARTICIPATION);

        mvc.perform(post("/api/participations")
                        .content(asJsonString(participationCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(String.format("http://**/participations/%s", PARTICIPATION.getId())))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.id").value(PARTICIPATION.getId()))
                .andExpect(jsonPath("$.courseId").value(participationCreationDto.getCourseId()))
                .andExpect(jsonPath("$.presences").value(participationCreationDto.getPresences()));
    }

    @Test
    public void createParticipationBadRequestTest() throws Exception {

        participationCreationDto.setCourseId(" ");
        participationCreationDto.setPresences(
                IntStream.range(0, 504)
                        .mapToObj(i -> new Presence("", "", false, new Date(), 10.0))
                        .toList()
        );

        when(participationService.createParticipation(ArgumentMatchers.any(ParticipationCreationDto.class)))
                .thenReturn(PARTICIPATION);

        mvc.perform(post("/api/participations")
                        .content(asJsonString(participationCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.courseId").value("CourseId can't be empty"))
                .andExpect(jsonPath("$.presences").value("Maximum size is 500"));
    }

    @Test
    public void updateParticipationOkTest() throws Exception {

        when(participationService.updateParticipation(
                ArgumentMatchers.eq(participationUpdateDto.getId()),
                ArgumentMatchers.any(ParticipationUpdateDto.class)
        )).thenReturn(PARTICIPATION);

        mvc.perform(put("/api/participations/{id}", PARTICIPATION.getId())
                        .content(asJsonString(participationUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.id").value(participationUpdateDto.getId()))
                .andExpect(jsonPath("$.courseId").value(participationUpdateDto.getCourseId()))
                .andExpect(jsonPath("$.presences").value(participationUpdateDto.getPresences()));
    }

    @Test
    public void updateParticipationBadRequestTest() throws Exception {

        participationUpdateDto.setId("");
        participationUpdateDto.setCourseId("");
        participationUpdateDto.setPresences(
                IntStream.range(0, 600)
                        .mapToObj(i -> new Presence("", "", false, new Date(), 1.0))
                        .toList()
        );

        when(participationService.updateParticipation(participationUpdateDto.getId(), participationUpdateDto))
                .thenThrow(IllegalArgumentException.class);

        mvc.perform(put("/api/participations/{id}", "unknown")
                        .content(asJsonString(participationUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.id").value("Id can't be empty"))
                .andExpect(jsonPath("$.courseId").value("CourseId can't be empty"))
                .andExpect(jsonPath("$.presences").value("Maximum size is 500"));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
