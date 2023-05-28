package com.spring.participations.participations.services;

import com.spring.participations.entities.Participation;
import com.spring.participations.exceptions.ParticipationNotFoundException;
import com.spring.participations.repositories.ParticipationRepository;
import com.spring.participations.services.ParticipationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParticipationServiceTest {

    @Autowired
    private ParticipationService participationService;

    @MockBean
    private ParticipationRepository participationRepository;

    private static final Participation PARTICIPATION = new Participation(
            "test-id",
            "course_id",
            List.of()
    );

    @Before
    public void setUp() {
        when(participationRepository.findById(PARTICIPATION.getId())).thenReturn(Optional.of(PARTICIPATION));
        when(participationRepository.findAll()).thenReturn(List.of(PARTICIPATION));
    }

    @Test
    public void getParticipationByIdTest() throws ParticipationNotFoundException {
        Participation found = participationService.getParticipation(PARTICIPATION.getId());
        assertNotNull(found);
    }

    @Test
    public void getParticipationByIdNotPresentTest() {
        assertThrows(ParticipationNotFoundException.class, () -> participationService.getParticipation(""));
    }

    @Test
    public void getParticipationsTest() {
        List<Participation> participations = participationService.getParticipations(null, null);
        assertEquals("List must have 1 participation", 1, participations.size());
    }
}
