package com.spring.badges.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.badges.dto.BadgeCreationDto;
import com.spring.badges.dto.BadgeUpdateDto;
import com.spring.badges.entities.Badge;
import com.spring.badges.exceptions.BadgeNotFoundException;
import com.spring.badges.services.BadgeService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BadgeController.class)
public class BadgeControllerTest {

    @Autowired
        private MockMvc mvc;

        @MockBean
        private BadgeService badgeService;

        private final ModelMapper modelMapper = new ModelMapper();

        private BadgeCreationDto badgeCreationDto;

        private BadgeUpdateDto badgeUpdateDto;

        private static final Badge BADGE = new Badge(
                "badge-id",
                "person-id"
        );

        @Before
        public void setUp() {
            badgeCreationDto = modelMapper.map(BADGE, BadgeCreationDto.class);
            badgeUpdateDto = modelMapper.map(BADGE, BadgeUpdateDto.class);
        }

        @Test
        public void getAllBadgesTest() throws Exception {

            when(badgeService.getBadges(BADGE.getIdPerson())).thenReturn(List.of(BADGE));

            mvc.perform(get("/api/badges")
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").exists())
                    .andExpect(jsonPath("$").isArray());
        }

        @Test
        public void getOneBadgeTest() throws Exception {

            when(badgeService.getBadge(BADGE.getId())).thenReturn(BADGE);

            mvc.perform(get("/api/badges/{id}", BADGE.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").exists())
                    .andExpect(jsonPath("$.*", hasSize(2)))
                    .andExpect(jsonPath("$.id").value(BADGE.getId()))
                    .andExpect(jsonPath("$.idPerson").value(BADGE.getIdPerson()));
        }

        @Test
        public void getOneBadgeNotFoundTest() throws Exception {

            when(badgeService.getBadge(BADGE.getId())).thenThrow(BadgeNotFoundException.class);

        mvc.perform(get("/api/badges/{id}", BADGE.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void createBadgeCreatedTest() throws Exception {

        when(badgeService.createBadge(ArgumentMatchers.any(BadgeCreationDto.class))).thenReturn(BADGE);

        mvc.perform(post("/api/badges")
                        .content(asJsonString(badgeCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(String.format("http://**/badges/%s", BADGE.getId())))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.id").value(BADGE.getId()))
                .andExpect(jsonPath("$.idPerson").value(badgeCreationDto.getIdPerson()));
    }

    @Test
    public void createBadgeBadRequestTest() throws Exception {

        badgeCreationDto = new BadgeCreationDto();

        when(badgeService.createBadge(ArgumentMatchers.any(BadgeCreationDto.class))).thenReturn(BADGE);

        mvc.perform(post("/api/badges")
                        .content(asJsonString(badgeCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.idPerson").value("IdPerson can't be empty"));
    }

    @Test
    public void updateBadgeOkTest() throws Exception {

        when(badgeService.updateBadge(
                ArgumentMatchers.eq(badgeUpdateDto.getId()),
                ArgumentMatchers.any(BadgeUpdateDto.class)
        )).thenReturn(BADGE);

        mvc.perform(put("/api/badges/{id}", badgeUpdateDto.getId())
                        .content(asJsonString(badgeUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.id").value(badgeUpdateDto.getId()))
                .andExpect(jsonPath("$.idPerson").value(badgeUpdateDto.getIdPerson()));
    }

    @Test
    public void updateBadgeBadRequestTest() throws Exception {

        badgeUpdateDto.setIdPerson("");

        when(badgeService.updateBadge(badgeUpdateDto.getId(), badgeUpdateDto)).thenReturn(BADGE);

        mvc.perform(put("/api/badges/{id}", badgeUpdateDto.getId())
                        .content(asJsonString(badgeUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.idPerson").value("IdPerson can't be empty"));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
