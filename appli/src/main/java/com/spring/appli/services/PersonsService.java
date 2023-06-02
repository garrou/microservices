package com.spring.appli.services;

import com.spring.appli.clients.PersonsClient;
import com.spring.appli.dto.Person;
import com.spring.appli.dto.PersonUpdateDto;
import com.spring.appli.exceptions.PersonNotFoundException;
import feign.FeignException;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PersonsService {

    @Autowired
    private PersonsClient personsClient;


    public List<Person> getPersons(Integer level, Integer levelSup, String pseudo) {
        return this.personsClient.getPersons(level, levelSup, pseudo).getBody();
    }

    public Person getPerson(UUID id) throws PersonNotFoundException {
        try {
            return this.personsClient.getPerson(String.valueOf(id));
        } catch (FeignException e) {
            if (e.status() == HttpStatus.SC_NOT_FOUND) {
                throw new PersonNotFoundException();
            }
            throw e;
        }
    }

    public Person updatePerson(UUID id, PersonUpdateDto person, String bearer) {
        return this.personsClient.updatePerson(id, person).getBody();
    }
}
