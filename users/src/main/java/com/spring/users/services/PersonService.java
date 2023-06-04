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

import java.util.ArrayList;
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

        if (personRepository.findOneByPseudo(personCreationDto.getPseudo()) != null) {
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
        Person foundPerson = personRepository.findOneByPseudo(personUpdateDto.getPseudo());

        if (foundPerson != null && foundPerson.getId() != personUpdateDto.getId()) {
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
            List<Person> list = new ArrayList<>();
            list.add(personRepository.findOneByPseudo(pseudo));
            return list;
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
        Person person = personRepository.findOneByPseudo(loginDto.getPseudo());
        if (person == null) {
            throw new PersonNotFoundException();
        }
        if (person.getPassword().equals(loginDto.getPassword())) {
            return person;
        }
        throw new WrongAuthentificationException();
    }

    public void deletePerson(UUID id) throws PersonNotFoundException {
        Optional<Person> courseOptinal = personRepository.findById(id);
        if(!courseOptinal.isPresent()){
            throw new PersonNotFoundException();
        }
        personRepository.deleteById(id);
    }
}
