package com.spring.appli.services;

import com.spring.appli.clients.ParticipationsClient;
import com.spring.appli.dto.*;
import com.spring.appli.exceptions.ParticipationNotFoundException;
import com.spring.appli.exceptions.PresenceNotFoundException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipationsService {

    @Autowired
    private ParticipationsClient participationsClient;

    //TODO add role control
    public Presence updatePresenceByParticipationId(String id, PresenceUpdateDto presenceUpdateDto, String presenceId) throws ParticipationNotFoundException, PresenceNotFoundException {
        return this.participationsClient.updatePresenceByParticipationId(id, presenceUpdateDto, presenceId).getBody();
    }

    public Presence createPresenceByParticipationId(String id, PresenceCreationDto presenceCreationDto) throws ParticipationNotFoundException {
        try {
            return this.participationsClient.createPresenceByParticipationId(id, presenceCreationDto).getBody();
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new ParticipationNotFoundException();
            }
            throw e;
        }
    }

    public List<Presence> getPresenceByParticipationId(String id) throws ParticipationNotFoundException {
        try {
            return this.participationsClient.getPresenceByParticipationId(id).getBody();
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new ParticipationNotFoundException();
            }
            throw e;
        }
    }

    public Participation updateParticipation(String id, ParticipationUpdateDto participation) throws ParticipationNotFoundException {
        try {
            return this.participationsClient.updateParticipation(id, participation).getBody();
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new ParticipationNotFoundException();
            }
            throw e;
        }
    }

    public Participation getParticipation(String id) throws ParticipationNotFoundException {
        try {
            return this.participationsClient.getParticipation(id).getBody();
        } catch (FeignException e) {
            if (e.status() == 404) {
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
}
