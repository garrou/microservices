package com.spring.users.services;

import com.spring.users.config.MapperDto;
import com.spring.users.dto.UserCreationDto;
import com.spring.users.entities.Person;
import com.spring.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MapperDto mapperDto;

    public Person createUser(UserCreationDto userCreationDto) {
        Person user = mapperDto.modelMapper().map(userCreationDto, Person.class);
        return userRepository.save(user);
    }

    public List<Person> getUsers() {
        return (List<Person>) userRepository.findAll();
    }
}
