package com.spring.participations.participations.config;

import com.spring.participations.config.MapperDto;
import com.spring.participations.dto.ParticipationCreationDto;
import com.spring.participations.dto.ParticipationUpdateDto;
import com.spring.participations.entities.Participation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MapperDtoTest {

    private MapperDto mapperDto;

    @BeforeEach
    public void setup() {
        mapperDto = new MapperDto();
    }

    @Test
    public void mappingCreationDtoToEntityTest() {
        ParticipationCreationDto participationCreationDto = new ParticipationCreationDto("feafegeg");
        Participation participation = mapperDto.modelMapper().map(participationCreationDto, Participation.class);

        assertNull(participation.getId(), "Id must be null");
        assertEquals(participationCreationDto.getCourseId(), participation.getCourseId(), "CourseId must be equal");
    }

    @Test
    public void mappingUpdateDtoToEntityTest() {
        ParticipationUpdateDto participationUpdateDto = new ParticipationUpdateDto("test-id", "1gefaaf0");
        Participation participation = mapperDto.modelMapper().map(participationUpdateDto, Participation.class);

        assertEquals(participationUpdateDto.getId(), participation.getId(), "Id must be equal");
        assertEquals(participationUpdateDto.getCourseId(), participation.getCourseId(), "CourseId must be equal");
    }
}
