package com.spring.appli.services;

import com.spring.appli.clients.ParticipationsClient;
import com.spring.appli.dto.*;
import com.spring.appli.exceptions.ParticipationNotFoundException;
import com.spring.appli.exceptions.PersonNotFoundException;
import com.spring.appli.exceptions.PresenceNotFoundException;
import feign.FeignException;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipationsService {

    @Autowired
    private ParticipationsClient participationsClient;

    public Presence updatePresenceByParticipationId(String id, PresenceUpdateDto presenceUpdateDto, String presenceId) throws ParticipationNotFoundException, PresenceNotFoundException {
        return this.participationsClient.updatePresenceByParticipationId(id, presenceUpdateDto, presenceId).getBody();
    }

    public Presence createPresenceByParticipationId(String id, PresenceCreationDto presenceCreationDto) throws ParticipationNotFoundException {
        try {
            return this.participationsClient.createPresenceByParticipationId(id, presenceCreationDto).getBody();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.SC_NOT_FOUND) {
                throw new ParticipationNotFoundException();
            }
            throw e;
        }
    }

    public List<Presence> getPresenceByParticipationId(String id) throws ParticipationNotFoundException {
        try {
            return this.participationsClient.getPresenceByParticipationId(id).getBody();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.SC_NOT_FOUND) {
                throw new ParticipationNotFoundException();
            }
            throw e;
        }
    }

    public Presence getPresenceByParticipationIdById(String participationId, String presenceId)
            throws ParticipationNotFoundException, PresenceNotFoundException {
        try {
            return this.participationsClient.getPresenceByParticipationIdById(participationId, presenceId).getBody();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.SC_NOT_FOUND) {
                throw new ParticipationNotFoundException();
            }
            throw e;
        }
    }

    public Participation updateParticipation(String id, ParticipationUpdateDto participation) throws ParticipationNotFoundException {
        try {
            return this.participationsClient.updateParticipation(id, participation).getBody();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.SC_NOT_FOUND) {
                throw new ParticipationNotFoundException();
            }
            throw e;
        }
    }

    public Participation getParticipation(String id) throws ParticipationNotFoundException {
        try {
            return this.participationsClient.getParticipation(id).getBody();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.SC_NOT_FOUND) {
                throw new ParticipationNotFoundException();
            }
            throw e;
        }
    }

    public List<Participation> getParticipations(String courseId, String badgeId) {
        return this.participationsClient.getParticipations(courseId, badgeId).getBody();
    }

    public Participation createParticipation(ParticipationCreationDto participation) {
        return this.participationsClient.createParticipation(participation).getBody();
    }

    public void deleteParticipation(String id) throws ParticipationNotFoundException {
        try{
            this.participationsClient.deleteParticipation(id);
        } catch(FeignException e){
            if(e.status() == HttpStatus.SC_NOT_FOUND){
                throw new ParticipationNotFoundException();
            }
            throw e;
        }
    }
}
