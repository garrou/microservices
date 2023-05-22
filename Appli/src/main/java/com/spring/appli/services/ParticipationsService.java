package com.spring.appli.services;

import com.spring.appli.clients.ParticipationsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipationsService {

    @Autowired
    private ParticipationsClient participationsClient;
}
