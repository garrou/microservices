package com.spring.appli.services;

import com.spring.appli.clients.CompetitionsClient;
import com.spring.appli.dto.Competition;
import com.spring.appli.dto.CompetitionCreationDto;
import com.spring.appli.dto.CompetitionUpdateDto;
import com.spring.appli.exceptions.CompetitionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompetitionsService {
    @Autowired
    private CompetitionsClient competitionsClient;

    //TODO add role control
    public List<Competition> getCompetitions(UUID teacherId, UUID studentId, Integer level) {
        return this.competitionsClient.getCompetitions(level, teacherId, studentId).getBody();
    }

    public Competition getCompetition(String id) throws CompetitionNotFoundException {
        return this.competitionsClient.getCompetition(id).getBody();
    }

    public List<UUID> getStudentsByCompetition(String id) throws CompetitionNotFoundException {
        return this.competitionsClient.getStudentsByCompetition(id).getBody();
    }

    public Competition updateCompetition(String id, CompetitionUpdateDto competition) throws CompetitionNotFoundException {
        return this.competitionsClient.updateCompetition(id, competition).getBody();
    }

    public Competition createCompetition(CompetitionCreationDto competition) {
        return this.competitionsClient.createCompetition(competition).getBody();
    }
}
