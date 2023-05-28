package com.spring.courses.config;

import com.spring.courses.dto.CourseCreationDto;
import com.spring.courses.dto.CourseUpdateDto;
import com.spring.courses.entities.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        CourseCreationDto courseCreationDto = new CourseCreationDto(
                "test",
                4,
                new Date(),
                120.0,
                "test",
                UUID.randomUUID(),
                List.of(),
                30
        );
        Course course = mapperDto.modelMapper().map(courseCreationDto, Course.class);

        assertNull(course.getId(), "Id must be null");
        assertEquals(courseCreationDto.getTitle(), course.getTitle(), "Title must be equal");
        assertEquals(courseCreationDto.getLevel(), course.getLevel(), "Level must be equal");
        assertEquals(courseCreationDto.getTimeSlot(), course.getTimeSlot(), "TimeSlot must be equal");
        assertEquals(courseCreationDto.getDuration(), course.getDuration(), "Duration must be equal");
        assertEquals(courseCreationDto.getLocation(), course.getLocation(), "Location must be equal");
        assertEquals(courseCreationDto.getTeacherId(), course.getTeacherId(), "TeacherId must be equal");
        assertEquals(courseCreationDto.getStudents(), course.getStudents(), "Students must be equal");
        assertEquals(courseCreationDto.getNbMaxStudents(), course.getNbMaxStudents(), "NbMaxStudents must be equal");
    }

    @Test
    public void mappingUpdateDtoToEntityTest() {
        CourseUpdateDto courseUpdateDto = new CourseUpdateDto(
                "id",
                "test",
                4,
                new Date(),
                120.0,
                "test",
                UUID.randomUUID(),
                List.of(),
                30
        );
        Course course = mapperDto.modelMapper().map(courseUpdateDto, Course.class);

        assertEquals(courseUpdateDto.getId(), course.getId(), "Id must be equal");
        assertEquals(courseUpdateDto.getTitle(), course.getTitle(), "Title must be equal");
        assertEquals(courseUpdateDto.getLevel(), course.getLevel(), "Level must be equal");
        assertEquals(courseUpdateDto.getTimeSlot(), course.getTimeSlot(), "TimeSlot must be equal");
        assertEquals(courseUpdateDto.getDuration(), course.getDuration(), "Duration must be equal");
        assertEquals(courseUpdateDto.getLocation(), course.getLocation(), "Location must be equal");
        assertEquals(courseUpdateDto.getTeacherId(), course.getTeacherId(), "TeacherId must be equal");
        assertEquals(courseUpdateDto.getStudents(), course.getStudents(), "Students must be equal");
        assertEquals(courseUpdateDto.getNbMaxStudents(), course.getNbMaxStudents(), "NbMaxStudents must be equal");
    }
}
