package com.sep3yg9.njorddata.services;

import com.sep3yg9.njorddata.grpc.protobuf.project.Requirement;
import com.sep3yg9.njorddata.grpc.protobuf.project.UpdatingProject;
import com.sep3yg9.njorddata.models.*;
import com.sep3yg9.njorddata.repos.*;
import com.sep3yg9.njorddata.services.interfaces.ProjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Optional;

@Service public class ProjectServiceImpl implements ProjectService
{
  private final ProjectRepository projectRepository;
  private final TeamRepository teamRepository;
  private final MemberRepository memberRepository;
  private final RequirementEntityRepository requirementRepository;
  private final LogbookEntityRepository logbookEntityRepository;

  public ProjectServiceImpl(ProjectRepository projectRepository,
      TeamRepository teamRepository, MemberRepository memberRepository,
      RequirementEntityRepository requirementRepository, LogbookEntityRepository logbookEntityRepository)
  {
    this.projectRepository = projectRepository;
    this.teamRepository = teamRepository;
    this.memberRepository = memberRepository;
    this.requirementRepository = requirementRepository;
    this.logbookEntityRepository = logbookEntityRepository;
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
          SpecificDateTimeConverter.convertToLocalDateTime(project.getDeadline())))
      {
        projectEntity.setDeadline(SpecificDateTimeConverter.convertToLocalDateTime(
            project.getDeadline()));
      }

      projectEntity.setRequirements(new LinkedHashSet<>());
      projectRepository.save(projectEntity);

      for (Requirement requirement : project.getRequirementsList())
      {
        RequirementEntity requirement1 = new RequirementEntity(projectEntity,
            requirement.getContent());
        projectEntity.addRequirement(requirement1);
        requirementRepository.save(requirement1);
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
    Optional<MemberEntity> memberEntity = memberRepository.findById(id);

    if(memberEntity.isEmpty()) {
      throw new IllegalArgumentException("Member not found");
    }

    MemberEntity member = memberEntity.get();

    for (TeamMember team : member.getTeams())
    {
      listOfProjects.addAll(projectRepository.findByTeamassigned_Idteam(
          team.getTeamEntity().getIdTeam()));
    }

    for (TeamEntity teamEntity : teamRepository.findByTeamleader(member)) {
      listOfProjects.addAll(projectRepository.findByTeamassigned_Idteam(teamEntity.getIdTeam()));
    }

    return listOfProjects;
  }
}
