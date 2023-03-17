package com.spring.users.services;

import com.spring.users.config.MapperDto;
import com.spring.users.dto.PersonCreationDto;
import com.spring.users.entities.Person;
import com.spring.users.exceptions.PersonNotFoundException;
import com.spring.users.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MapperDto mapperDto;

    public Person createPerson(PersonCreationDto personCreationDto) {
        Person person = mapperDto.modelMapper().map(personCreationDto, Person.class);
        return personRepository.save(person);
    }

    public List<Person> getPersons() {
        return (List<Person>) personRepository.findAll();
    }

    public Person getPerson(UUID id) throws PersonNotFoundException {
        Optional<Person> person = personRepository.findById(id);

        if (person.isEmpty()) {
            throw new PersonNotFoundException();
        }
        return person.get();
    }
}
