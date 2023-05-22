package com.spring.appli.services;

import com.spring.appli.clients.CompetitionsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitionsService {
    @Autowired
    private CompetitionsClient competitionsClient;
}
