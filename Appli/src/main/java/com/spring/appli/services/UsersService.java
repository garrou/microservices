package com.spring.appli.services;

import com.spring.appli.clients.UsersClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersClient usersClient;
}
