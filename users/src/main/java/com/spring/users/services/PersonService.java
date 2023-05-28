package com.spring.users.services;

import com.spring.users.configs.MapperDto;
import com.spring.users.dto.LoginDto;
import com.spring.users.dto.PersonCreationDto;
import com.spring.users.dto.PersonUpdateDto;
import com.spring.users.entities.Person;
import com.spring.users.exceptions.PersonNotFoundException;
import com.spring.users.exceptions.PseudoAlreadyExistException;
import com.spring.users.exceptions.WrongAuthentificationException;
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

    public Person createPerson(PersonCreationDto personCreationDto) throws PseudoAlreadyExistException {

        if (!personRepository.findAllByPseudo(personCreationDto.getPseudo()).isEmpty()) {
            throw new PseudoAlreadyExistException();
        }
        Person person = mapperDto.modelMapper().map(personCreationDto, Person.class);
        return personRepository.save(person);

    }

    public Person updatePerson(UUID id, PersonUpdateDto personUpdateDto) throws PersonNotFoundException, PseudoAlreadyExistException {

        if (!id.equals(personUpdateDto.getId())) {
            throw new IllegalArgumentException();
        }
        if (personRepository.findById(personUpdateDto.getId()).isEmpty()) {
            throw new PersonNotFoundException();
        }
        if (!personRepository.findAllByPseudo(personUpdateDto.getPseudo()).isEmpty()) {
            throw new PseudoAlreadyExistException();
        }
        Person person = mapperDto.modelMapper().map(personUpdateDto, Person.class);
        return personRepository.save(person);

    }

    public List<Person> getPersons(Integer level, Integer levelSup, String pseudo) {

        if (level != null && levelSup != null) {
            throw new IllegalArgumentException();
        }
        if (pseudo != null && level != null) {
            return personRepository.findAllByPseudoAndLevel(pseudo, level);
        } else if (pseudo != null && levelSup != null) {
            return personRepository.findAllByPseudoAndLevelGreaterThan(pseudo, levelSup);
        } else if (level != null) {
            return personRepository.findAllByLevel(level);
        } else if (levelSup != null) {
            return personRepository.findAllByLevelGreaterThan(levelSup);
        } else if (pseudo != null) {
            return personRepository.findAllByPseudo(pseudo);
        }
        return (List<Person>) personRepository.findAll();
    }

    public Person getPerson(UUID id) throws PersonNotFoundException {

        Optional<Person> person = personRepository.findById(id);

        if (person.isEmpty()) {
            throw new PersonNotFoundException();
        }
        return person.get();
    }

    public Person login(LoginDto loginDto) throws PersonNotFoundException, WrongAuthentificationException {
        List<Person> person = personRepository.findAllByPseudo(loginDto.getPseudo());
        if (person.isEmpty()) {
            throw new PersonNotFoundException();
        }
        if (person.get(0).getPassword().equals(loginDto.getPassword())) {
            return person.get(0);
        }
        throw new WrongAuthentificationException();
    }
}
