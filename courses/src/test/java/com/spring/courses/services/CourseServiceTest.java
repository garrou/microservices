package com.spring.courses.services;

import com.spring.courses.entities.Course;
import com.spring.courses.exceptions.CourseNotFoundException;
import com.spring.courses.repositories.CourseRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @MockBean
    private CourseRepository courseRepository;

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
        when(courseRepository.findById(COURSE.getId())).thenReturn(Optional.of(COURSE));
    }

    @Test
    public void getCourseByIdTest() throws CourseNotFoundException {
        Course found = courseService.getCourse(COURSE.getId());
        assertNotNull(found);
    }

    @Test
    public void getCourseByIdNotPresentTest() {
        assertThrows(CourseNotFoundException.class, () -> courseService.getCourse(""));
    }
}
