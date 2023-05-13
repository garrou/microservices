package com.spring.badges.config;

import com.spring.badges.dto.BadgeCreationDto;
import com.spring.badges.dto.BadgeUpdateDto;
import com.spring.badges.entities.Badge;
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
        BadgeCreationDto badgeCreationDto = new BadgeCreationDto("10");
        Badge badge = mapperDto.modelMapper().map(badgeCreationDto, Badge.class);

        assertNull(badge.getId(), "is must be null");
        assertEquals(badgeCreationDto.getIdPerson(), badge.getIdPerson(), "idPerson must be equal");
    }

    @Test
    public void mappingUpdateDtoToEntityTest() {
        BadgeUpdateDto badgeUpdateDto = new BadgeUpdateDto("1", "10");
        Badge badge = mapperDto.modelMapper().map(badgeUpdateDto, Badge.class);

        assertEquals(badgeUpdateDto.getId(), badge.getId(), "id must be equal");
        assertEquals(badgeUpdateDto.getIdPerson(), badge.getIdPerson(), "idPerson must be equal");
    }
}
