package com.spring.appli.services;

import com.spring.appli.clients.PersonsClient;
import com.spring.appli.configs.MapperDto;
import com.spring.appli.dto.LoginDto;
import com.spring.appli.dto.Person;
import com.spring.appli.exceptions.PersonNotFoundException;
import com.spring.appli.exceptions.WrongAuthentificationException;
import feign.FeignException;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private PersonsClient authClient;

    @Autowired
    private MapperDto mapperDto;

    public String login(LoginDto loginDto) throws PersonNotFoundException, WrongAuthentificationException {
        try {
            Person person = this.authClient.login(loginDto).getBody();
            return person.getId() + "+" + person.getRole();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.SC_UNAUTHORIZED) {
                throw new WrongAuthentificationException();
            } else if (e.status() == HttpStatus.SC_NOT_FOUND) {
                throw new PersonNotFoundException();
            }
            throw e;
        }

    }

}
