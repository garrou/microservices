package com.spring.appli.services;

import com.spring.appli.clients.StatisticsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    @Autowired
    private StatisticsClient statisticsClient;
}
