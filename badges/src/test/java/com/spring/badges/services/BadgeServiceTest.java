package com.spring.badges.services;

import com.spring.badges.entities.Badge;
import com.spring.badges.exceptions.BadgeNotFoundException;
import com.spring.badges.repositories.BadgeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BadgeServiceTest {

    @Autowired
    private BadgeService badgeService;

    @MockBean
    private BadgeRepository badgeRepository;

    private static final Badge BADGE = new Badge(
            "id",
            "person-id"
    );

    @Before
    public void setUp() {
        when(badgeRepository.findById(BADGE.getId())).thenReturn(Optional.of(BADGE));
    }

    @Test
    public void getBadgeByIdTest() throws BadgeNotFoundException {
        Badge found = badgeService.getBadge(BADGE.getId());
        assertNotNull(found);
    }

    @Test
    public void getBadgeByIdNotPresentTest() {
        assertThrows(BadgeNotFoundException.class, () -> badgeService.getBadge(""));
    }
}
