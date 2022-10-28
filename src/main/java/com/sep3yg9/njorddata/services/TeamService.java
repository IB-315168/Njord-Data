package com.sep3yg9.njorddata.services;

import com.sep3yg9.njorddata.models.TeamEntity;
import com.sep3yg9.njorddata.repos.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TeamService
{
    @Autowired
    private TeamRepository teamRepository;

    public void addTeam(TeamEntity teamEntityRecord){
        teamRepository.save(teamEntityRecord);
    }

    public TeamEntity getById(int id){
        return teamRepository.findByIdTeam(id);
    }

    public TeamEntity getByName(String name){
        return teamRepository.findByName(name);
    }
}
