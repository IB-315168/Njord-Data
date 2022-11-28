package com.sep3yg9.njorddata.models;

import com.sep3yg9.njorddata.grpc.protobuf.project.BasicProject;
import com.sep3yg9.njorddata.grpc.protobuf.project.ProjectGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.project.Requirement;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "Project") @Table(name = "project", schema = "sep3ygroup9") public class ProjectEntity
{
  @Id @GeneratedValue @Column(name = "idproject") private int idproject;
  private String name;
  private LocalDateTime startdate;
  private LocalDateTime deadline;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "idproject", cascade = CascadeType.ALL, orphanRemoval = true) private Set<RequirementEntity> requirements = new LinkedHashSet<>();

  @ManyToOne(fetch = FetchType.LAZY) @OnDelete(action = OnDeleteAction.CASCADE) @JoinColumn(name = "teamassigned") private TeamEntity teamassigned;

  public TeamEntity getTeamassigned()
  {
    return teamassigned;
  }

  public void setTeamassigned(TeamEntity teamassigned)
  {
    this.teamassigned = teamassigned;
  }

  public ProjectEntity()
  {
  }

  public ProjectEntity(TeamEntity teamAssigned, String name, LocalDateTime startdate,
      LocalDateTime deadline)
  {
    this.teamassigned = teamAssigned;
    this.name = name;
    this.startdate = startdate;
    this.deadline = deadline;
  }

  public int getIdproject()
  {
    return idproject;
  }

  public void setIdproject(int idproject)
  {
    this.idproject = idproject;
  }

  public TeamEntity getTeamAssigned()
  {
    return teamassigned;
  }

  public void setTeamAssigned(TeamEntity teamAssigned)
  {
    this.teamassigned = teamAssigned;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public LocalDateTime getStartdate()
  {
    return startdate;
  }

  public void setStartdate(LocalDateTime startdate)
  {
    this.startdate = startdate;
  }

  public LocalDateTime getDeadline()
  {
    return deadline;
  }

  public void setDeadline(LocalDateTime deadline)
  {
    this.deadline = deadline;
  }

  public Set<RequirementEntity> getRequirements()
  {
    return requirements;
  }

  public void setRequirements(Set<RequirementEntity> requirements)
  {
    this.requirements = requirements;
  }

  public void addRequirement(RequirementEntity requirement) {
    requirements.add(requirement);
    requirement.setIdproject(this);
  }

  public void removeRequirement(RequirementEntity requirement)
  {
    requirements.remove(requirement);
    requirement.setIdproject(null);
  }

  public ProjectGrpc convertToProjectGrpc()
  {
    Set<Requirement> requirements1 = new LinkedHashSet<>();

    for(RequirementEntity requirement : requirements) {
      requirements1.add(requirement.convertToRequirement());
    }

    return ProjectGrpc.newBuilder().setId(idproject).setName(name)
        .setTeamId(teamassigned.convertToTeamGrpc())
        .setStartDate(SpecificDateTimeConverter.convertToSpecificDateTime(startdate))
        .setDeadline(SpecificDateTimeConverter.convertToSpecificDateTime(deadline))
        .addAllRequirements(requirements1)
        .build();
  }

  public BasicProject convertToBasicProject()
  {
    return BasicProject.newBuilder()
            .setId(idproject)
            .setProjectName(name)
            .setTeamName(teamassigned.getName())
        .build();     //todo: How do I find the value for the team name without team repository.
  }

  @Override public String toString()
  {
    return "Project: " + name + "_" + idproject + "; /n Team Assigned ID:"
        + teamassigned + "; Started at " + startdate.toString()
        + " with deadline of " + deadline.toString();
  }

  @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ProjectEntity that = (ProjectEntity) o;
    return idproject == that.idproject && teamassigned == that.teamassigned
        && startdate.equals(that.startdate) && deadline.equals(that.deadline)
        && Objects.equals(name, that.name) && Objects.equals(requirements,
        that.requirements);
  }

  @Override public int hashCode()
  {
    return Objects.hash(idproject, teamassigned, name, startdate, deadline,
        requirements);
  }
}
