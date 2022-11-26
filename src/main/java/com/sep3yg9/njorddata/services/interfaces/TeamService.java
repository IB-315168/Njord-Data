package com.sep3yg9.njorddata.services.interfaces;

import com.sep3yg9.njorddata.grpc.protobuf.team.UpdatingTeam;
import com.sep3yg9.njorddata.models.TeamEntity;

import java.util.List;

public interface TeamService
{
    void addTeam(TeamEntity teamEntityRecord);
    void updateTeam(UpdatingTeam team);
    void removeTeam(int id);
    TeamEntity getById(int id);
    TeamEntity getByName(String name);
    List<TeamEntity> getByTeamLeaderId(int id);
}
