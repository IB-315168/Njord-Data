package com.sep3yg9.njorddata.services;

import com.google.protobuf.Int32Value;
import com.google.protobuf.Int32ValueOrBuilder;
import com.sep3yg9.njorddata.grpc.protobuf.team.UpdatingTeam;
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
        return teamRepository.findByIdteam(id);
    }

    public TeamEntity getByName(String name){
        return teamRepository.findByName(name);
    }
    public void removeTeam(int id){
        teamRepository.deleteById(id);
    }

    public void updateTeam(UpdatingTeam team){
        TeamEntity teamEntity = teamRepository.findByIdteam(team.getId());

        if(teamEntity == null){
            System.out.println("Team does not exist");
        }
        else{
            if(!team.getName().isEmpty() && !team.getName().equals(teamEntity.getName())){
                teamEntity.setName(team.getName());
            }
        }

        teamRepository.save(teamEntity);
    }
}
