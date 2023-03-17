package com.spring.users.services;

import com.spring.users.config.MapperDto;
import com.spring.users.dto.UserCreationDto;
import com.spring.users.entities.User;
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

    public User createUser(UserCreationDto userCreationDto) {
        User user = mapperDto.modelMapper().map(userCreationDto, User.class);
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }
}
