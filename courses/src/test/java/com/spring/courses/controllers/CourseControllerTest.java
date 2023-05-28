package com.spring.courses.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.courses.dto.CourseCreationDto;
import com.spring.courses.dto.CourseUpdateDto;
import com.spring.courses.entities.Course;
import com.spring.courses.exceptions.CourseNotFoundException;
import com.spring.courses.services.CourseService;
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
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(CourseController.class)
public class CourseControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private CourseService courseService;

    private final ModelMapper modelMapper = new ModelMapper();

    private CourseCreationDto courseCreationDto;

    private CourseUpdateDto courseUpdateDto;

    private static final Course COURSE = new Course(
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

    @Before
    public void setUp() {
        courseCreationDto = modelMapper.map(COURSE, CourseCreationDto.class);
        courseUpdateDto = modelMapper.map(COURSE, CourseUpdateDto.class);
    }

    @Test
    public void getAllCoursesTest() throws Exception {

        when(courseService.getCourses(null, null, null)).thenReturn(List.of(COURSE));

        mvc.perform(get("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getOneCourseTest() throws Exception {

        when(courseService.getCourse(COURSE.getId())).thenReturn(COURSE);

        mvc.perform(get("/api/courses/{id}", COURSE.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(9)))
                .andExpect(jsonPath("$.id").value(COURSE.getId()))
                .andExpect(jsonPath("$.title").value(COURSE.getTitle()))
                .andExpect(jsonPath("$.level").value(COURSE.getLevel()))
                .andExpect(jsonPath("$.teacherId").value(COURSE.getTeacherId().toString()))
                .andExpect(jsonPath("$.timeSlot").exists())
                .andExpect(jsonPath("$.duration").value(COURSE.getDuration()))
                .andExpect(jsonPath("$.location").value(COURSE.getLocation()))
                .andExpect(jsonPath("$.students").exists())
                .andExpect(jsonPath("$.nbMaxStudents").value(COURSE.getNbMaxStudents()));
    }

    @Test
    public void getOneCourseNotFoundTest() throws Exception {

        when(courseService.getCourse(COURSE.getId())).thenThrow(CourseNotFoundException.class);

        mvc.perform(get("/api/courses/{id}", COURSE.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void createCourseCreatedTest() throws Exception {

        when(courseService.createCourse(ArgumentMatchers.any(CourseCreationDto.class))).thenReturn(COURSE);

        mvc.perform(post("/api/courses")
                        .content(asJsonString(courseCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(String.format("http://**/courses/%s", COURSE.getId())))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(9)))
                .andExpect(jsonPath("$.id").value(COURSE.getId()))
                .andExpect(jsonPath("$.title").value(COURSE.getTitle()))
                .andExpect(jsonPath("$.level").value(COURSE.getLevel()))
                .andExpect(jsonPath("$.teacherId").value(COURSE.getTeacherId().toString()))
                .andExpect(jsonPath("$.timeSlot").exists())
                .andExpect(jsonPath("$.duration").value(COURSE.getDuration()))
                .andExpect(jsonPath("$.location").value(COURSE.getLocation()))
                .andExpect(jsonPath("$.students").exists())
                .andExpect(jsonPath("$.nbMaxStudents").value(COURSE.getNbMaxStudents()));
    }

    @Test
    public void createCourseBadRequestTest() throws Exception {

        courseCreationDto = new CourseCreationDto();

        when(courseService.createCourse(ArgumentMatchers.any(CourseCreationDto.class))).thenReturn(COURSE);

        mvc.perform(post("/api/courses")
                        .content(asJsonString(courseCreationDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(5)))
                .andExpect(jsonPath("$.title").value("Title can't be empty"))
                .andExpect(jsonPath("$.duration").value("Minimum duration is 1.0"))
                .andExpect(jsonPath("$.teacherId").value("TeacherId is invalid"))
                .andExpect(jsonPath("$.nbMaxStudents").value("Minimum value is 1"))
                .andExpect(jsonPath("$.location").value("Location can't be empty"));
    }

    @Test
    public void updateCourseOkTest() throws Exception {

        when(courseService.updateCourse(
                ArgumentMatchers.eq(courseUpdateDto.getId()),
                ArgumentMatchers.any(CourseUpdateDto.class)
        )).thenReturn(COURSE);

        mvc.perform(put("/api/courses/{id}", courseUpdateDto.getId())
                        .content(asJsonString(courseUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(9)))
                .andExpect(jsonPath("$.id").value(courseUpdateDto.getId()))
                .andExpect(jsonPath("$.title").value(courseUpdateDto.getTitle()))
                .andExpect(jsonPath("$.level").value(courseUpdateDto.getLevel()))
                .andExpect(jsonPath("$.teacherId").value(courseUpdateDto.getTeacherId().toString()))
                .andExpect(jsonPath("$.timeSlot").exists())
                .andExpect(jsonPath("$.duration").value(courseUpdateDto.getDuration()))
                .andExpect(jsonPath("$.location").value(courseUpdateDto.getLocation()))
                .andExpect(jsonPath("$.students").value(courseUpdateDto.getStudents()))
                .andExpect(jsonPath("$.nbMaxStudents").value(courseUpdateDto.getNbMaxStudents()));
    }

    @Test
    public void updateCourseBadRequestTest() throws Exception {

        courseUpdateDto = new CourseUpdateDto();
        courseUpdateDto.setId("id");

        when(courseService.updateCourse(
                ArgumentMatchers.eq(courseUpdateDto.getId()),
                ArgumentMatchers.any(CourseUpdateDto.class)
        )).thenReturn(COURSE);

        mvc.perform(put("/api/courses/{id}", courseUpdateDto.getId())
                        .content(asJsonString(courseUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.*", hasSize(5)))
                .andExpect(jsonPath("$.title").value("Title can't be empty"))
                .andExpect(jsonPath("$.duration").value("Minimum duration is 1.0"))
                .andExpect(jsonPath("$.teacherId").value("TeacherId is invalid"))
                .andExpect(jsonPath("$.nbMaxStudents").value("Minimum value is 1"))
                .andExpect(jsonPath("$.location").value("Location can't be empty"));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
