package com.spring.appli.services;

import com.spring.appli.clients.BadgesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BadgesService {
    @Autowired
    private BadgesClient badgesClient;

}
