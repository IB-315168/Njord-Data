package com.sep3yg9.njorddata.services;

import com.sep3yg9.njorddata.grpc.protobuf.project.Requirement;
import com.sep3yg9.njorddata.grpc.protobuf.project.UpdatingProject;
import com.sep3yg9.njorddata.models.*;
import com.sep3yg9.njorddata.repos.ProjectRepository;
import com.sep3yg9.njorddata.repos.TeamRepository;
import com.sep3yg9.njorddata.repos.UserRepository;
import com.sep3yg9.njorddata.services.interfaces.ProjectService;
import com.sep3yg9.njorddata.services.interfaces.TeamService;
import com.sep3yg9.njorddata.services.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Service public class ProjectServiceImpl implements ProjectService
{
  private final ProjectRepository projectRepository;
  private final TeamRepository teamRepository;
  private final UserRepository userRepository;

  public ProjectServiceImpl(ProjectRepository projectRepository,
      TeamRepository teamRepository, UserRepository userRepository)
  {
    this.projectRepository = projectRepository;
    this.teamRepository = teamRepository;
    this.userRepository = userRepository;
  }

  @Override public ProjectEntity addProject(ProjectEntity projectEntityRecord)
  {
    return projectRepository.save(projectEntityRecord);
  }

  @Override public void updateProject(UpdatingProject project)
  {
    ProjectEntity projectEntity = projectRepository.findByIdproject(
        project.getId());

    if (projectEntity == null)
    {
      throw new IllegalArgumentException("Project not found");
    }
    else
    {
      if (!project.getName().isEmpty() && !projectEntity.getName()
          .equals(project.getName()))
      {
        projectEntity.setName(project.getName());
      }

      if (!projectEntity.getDeadline().equals(
          SpecificTimeConverter.convertToLocalDateTime(project.getDeadline())))
      {
        projectEntity.setDeadline(SpecificTimeConverter.convertToLocalDateTime(
            project.getDeadline()));
      }

      projectEntity.setRequirements(new LinkedHashSet<>());
      projectRepository.save(projectEntity);

      //            Set<RequirementEntity> requirementSet = new LinkedHashSet<>();
      for (Requirement requirement : project.getRequirementsList())
      {
        projectEntity.addRequirement(
            new RequirementEntity(requirement.getId(), projectEntity,
                requirement.getContent()));
      }

      projectRepository.save(projectEntity);
    }
  }

  @Override public void removeProject(int id)
  {
    getById(id);

    projectRepository.deleteById(id);
  }

  @Override public ProjectEntity getById(int id)
  {
    ProjectEntity projectEntity = projectRepository.findByIdproject(id);

    if (projectEntity == null)
    {
      throw new IllegalArgumentException("Project not found");
    }

    return projectRepository.findByIdproject(id);
  }

  @Override public ArrayList<ProjectEntity> getByUserId(int id)
  {
    ArrayList<ProjectEntity> listOfProjects = new ArrayList<>();
    UserEntity user = userRepository.findById(id);

    for (TeamMember team : user.getTeams())
    {
      listOfProjects.addAll(projectRepository.findByTeamassigned_Idteam(
          team.getTeamEntity().getIdTeam()));
    }

    for (TeamEntity teamEntity : teamRepository.findByTeamleader(user)) {
      listOfProjects.addAll(projectRepository.findByTeamassigned_Idteam(teamEntity.getIdTeam()));
    }

    return listOfProjects;
  }
}
