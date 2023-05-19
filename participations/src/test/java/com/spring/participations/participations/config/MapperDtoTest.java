package com.spring.participations.participations.config;

import com.spring.participations.config.MapperDto;
import com.spring.participations.dto.ParticipationCreationDto;
import com.spring.participations.dto.ParticipationUpdateDto;
import com.spring.participations.entities.Participation;
import com.spring.participations.entities.Presence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        ParticipationCreationDto participationCreationDto = new ParticipationCreationDto("feafegeg", new ArrayList<>());
        Participation participation = mapperDto.modelMapper().map(participationCreationDto, Participation.class);

        assertNull(participation.getId(), "Id must be null");
        assertEquals(participationCreationDto.getCourseId(), participation.getCourseId(), "CourseId must be equal");
    }

    @Test
    public void mappingUpdateDtoToEntityTest() {
        List<Presence> presenceList = new ArrayList<>();
        presenceList.add(new Presence("1", "1", true, new Date(), 0.0));
        ParticipationUpdateDto participationUpdateDto = new ParticipationUpdateDto("test-id", "1gefaaf0", presenceList);
        Participation participation = mapperDto.modelMapper().map(participationUpdateDto, Participation.class);

        assertEquals(participationUpdateDto.getId(), participation.getId(), "Id must be equal");
        assertEquals(participationUpdateDto.getCourseId(), participation.getCourseId(), "CourseId must be equal");
        assertEquals(participationUpdateDto.getPresenceList(), participation.getPresenceList(), "PresenceList must be equal");
    }
}
