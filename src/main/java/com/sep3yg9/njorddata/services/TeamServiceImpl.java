package com.sep3yg9.njorddata.services;

import com.sep3yg9.njorddata.grpc.protobuf.team.UpdatingTeam;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import com.sep3yg9.njorddata.models.TeamEntity;
import com.sep3yg9.njorddata.models.TeamMember;
import com.sep3yg9.njorddata.models.UserEntity;
import com.sep3yg9.njorddata.repos.TeamRepository;
import com.sep3yg9.njorddata.repos.UserRepository;
import com.sep3yg9.njorddata.services.interfaces.TeamService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service public class TeamServiceImpl implements TeamService
{
  private final TeamRepository teamRepository;
  private final UserRepository userRepository;

  public TeamServiceImpl(TeamRepository teamRepository,
      UserRepository userRepository)
  {
    this.teamRepository = teamRepository;
    this.userRepository = userRepository;
  }

  public void addTeam(TeamEntity teamEntityRecord)
  {
    TeamEntity nameCheck = teamRepository.findByName(
        teamEntityRecord.getName());

    if (nameCheck != null)
    {
      throw new IllegalArgumentException("Name is in use");
    }

    teamRepository.save(teamEntityRecord);
  }

  public TeamEntity getById(int id)
  {
    TeamEntity teamEntity = teamRepository.findByIdteam(id);

    if (teamEntity == null)
    {
      throw new IllegalArgumentException("Team not found");
    }

    return teamEntity;
  }

  public List<TeamEntity> getByTeamLeaderId(int id)
  {
    return teamRepository.findByTeamleader(id);
  }

  public TeamEntity getByName(String name)
  {
    TeamEntity teamEntity = teamRepository.findByName(name);

    if (teamEntity == null)
    {
      throw new IllegalArgumentException("Team not found");
    }

    return teamEntity;
  }

  public void removeTeam(int id)
  {
    getById(id);
    teamRepository.deleteById(id);
  }

  public void updateTeam(UpdatingTeam team)
  {
    TeamEntity teamEntity = getById(team.getId());

    if (!team.getName().isEmpty() && !team.getName()
        .equals(teamEntity.getName()))
    {
      TeamEntity nameCheck = teamRepository.findByName(team.getName());

      if (nameCheck != null)
      {
        throw new IllegalArgumentException("Name is in use");
      }

      teamEntity.setName(team.getName());
    }

    ArrayList<UserEntity> newMembers = new ArrayList<>();
    for (User user : team.getMembersList())
    {
      newMembers.add(userRepository.findById(user.getId()));
    }

    teamEntity.setMembers(new ArrayList<TeamMember>());
    teamRepository.save(teamEntity);

    for (UserEntity user : newMembers)
    {
      teamEntity.addMember(user);
    }

    teamRepository.save(teamEntity);
  }
}
