package com.spring.appli.services;

import com.spring.appli.clients.CompetitionsClient;
import com.spring.appli.dto.Competition;
import com.spring.appli.dto.CompetitionCreationDto;
import com.spring.appli.dto.CompetitionUpdateDto;
import com.spring.appli.exceptions.CompetitionNotFoundException;
import feign.FeignException;
import org.apache.http.HttpStatus;
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
        try {
            return this.competitionsClient.getCompetition(id).getBody();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.SC_NOT_FOUND) {
                throw new CompetitionNotFoundException();
            }
            throw e;
        }
    }

    public List<UUID> getStudentsByCompetition(String id) throws CompetitionNotFoundException {
        try {
            return this.competitionsClient.getStudentsByCompetition(id).getBody();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.SC_NOT_FOUND) {
                throw new CompetitionNotFoundException();
            }
            throw e;
        }
    }

    public Competition updateCompetition(String id, CompetitionUpdateDto competition) throws CompetitionNotFoundException {
        try {
            return this.competitionsClient.updateCompetition(id, competition).getBody();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.SC_NOT_FOUND) {
                throw new CompetitionNotFoundException();
            }
            throw e;
        }

    }

    public Competition createCompetition(CompetitionCreationDto competition) {
        return this.competitionsClient.createCompetition(competition).getBody();
    }
}
