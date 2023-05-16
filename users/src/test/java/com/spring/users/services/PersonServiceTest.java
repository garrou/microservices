package com.spring.users.services;

import com.spring.users.entities.Person;
import com.spring.users.enums.Role;
import com.spring.users.exceptions.PersonNotFoundException;
import com.spring.users.repositories.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    private static final Person PERSON = new Person(
            UUID.randomUUID(),
            "test-firstname",
            "test-lastname",
            "test-pseudo",
            "test-email@gmail.com",
            "test-address",
            3,
            Role.TEACHER,
            "test-test"
    );

    @Before
    public void setUp() {
        when(personRepository.findById(PERSON.getId())).thenReturn(Optional.of(PERSON));
    }

    @Test
    public void getPersonByIdTest() throws PersonNotFoundException {
        Person found = personService.getPerson(PERSON.getId());
        assertNotNull(found);
    }

    @Test
    public void getPersonByIdNotPresentTest() {
        assertThrows(PersonNotFoundException.class, () -> personService.getPerson(UUID.randomUUID()));
    }
}
