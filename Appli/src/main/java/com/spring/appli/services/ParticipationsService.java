package com.spring.appli.services;

import com.spring.appli.clients.ParticipationsClient;
import com.spring.appli.dto.*;
import com.spring.appli.exceptions.ParticipationNotFoundException;
import com.spring.appli.exceptions.PresenceNotFoundException;
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
        return this.participationsClient.createPresenceByParticipationId(id, presenceCreationDto).getBody();
    }

    public List<Presence> getPresenceByParticipationId(String id) throws ParticipationNotFoundException {
        return this.participationsClient.getPresenceByParticipationId(id).getBody();
    }

    public Participation updateParticipation(String id, ParticipationUpdateDto participation) throws ParticipationNotFoundException {
        return this.participationsClient.updateParticipation(id, participation).getBody();
    }

    public Participation getParticipation(String id) throws ParticipationNotFoundException {
        return this.participationsClient.getParticipation(id).getBody();
    }

    public List<Participation> getParticipations(String courseId, String badgeId) {
        return this.participationsClient.getParticipations(courseId, badgeId).getBody();
    }

    public Participation createParticipation(ParticipationCreationDto participation) {
        return this.participationsClient.createParticipation(participation).getBody();
    }
}
