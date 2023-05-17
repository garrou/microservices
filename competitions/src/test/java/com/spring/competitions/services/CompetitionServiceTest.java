package com.spring.competitions.services;

import com.spring.competitions.entities.Competition;
import com.spring.competitions.exceptions.CompetitionNotFoundException;
import com.spring.competitions.repositories.CompetitionRepository;
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
public class CompetitionServiceTest {

    @Autowired
    private CompetitionService competitionService;

    @MockBean
    private CompetitionRepository competitionRepository;

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
        when(competitionRepository.findById(COMPETITION.getId())).thenReturn(Optional.of(COMPETITION));
    }

    @Test
    public void getPersonByIdTest() throws CompetitionNotFoundException {
        Competition found = competitionService.getCompetition(COMPETITION.getId());
        assertNotNull(found);
    }

    @Test
    public void getPersonByIdNotPresentTest() {
        assertThrows(CompetitionNotFoundException.class, () -> competitionService.getCompetition("unknown"));
    }
}
