package com.sep3yg9.njorddata.models;

import com.google.type.DateTime;
import com.sep3yg9.njorddata.grpc.protobuf.project.Project;
import com.sep3yg9.njorddata.grpc.protobuf.project.SpecificTime;
import com.sep3yg9.njorddata.grpc.protobuf.user.BasicTeam;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "Project") @Table(name = "project", schema = "sep3ygroup9")
public class ProjectEntity
{
    @Id @GeneratedValue @Column(name="idproject") private int idproject;

    private int teamAssigned;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime  deadline;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idproject", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RequirementEntity> requirements = new LinkedHashSet<>();

    public ProjectEntity(){
    }

    public ProjectEntity(int teamAssigned, String name, LocalDateTime  startDate, LocalDateTime  deadline, Set<RequirementEntity> requirements){
        this.teamAssigned = teamAssigned;
        this.name = name;
        this.startDate = startDate;
        this.deadline = deadline;
        this.requirements = requirements;
    }

    public int getIdproject() {
        return idproject;
    }

    public void setIdproject(int idproject) {
        this.idproject = idproject;
    }

    public int getTeamAssigned() {
        return teamAssigned;
    }

    public void setTeamAssigned(int teamAssigned) {
        this.teamAssigned = teamAssigned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Set<RequirementEntity> getRequirements() {
        return requirements;
    }

    public void addRequirement(RequirementEntity requirement){
        this.requirements.add(requirement);
    }

    public void removeRequirement(RequirementEntity requirement){
        this.requirements.remove(requirement);
    }
    public void setRequirements(Set<RequirementEntity> requirements) {
        this.requirements = requirements;
    }

    public Project convertToProject() {
        return Project.newBuilder()
                .setId(idproject)
                .setName(name)
                .setTeamId(teamAssigned)
                .setDeadline(SpecificTime.newBuilder().
                        setDay(deadline.getDayOfMonth())
                        .setMonth(deadline.getMonthValue())
                        .setYear(deadline.getYear())
                        .setHour(deadline.getHour())
                        .setMinute(deadline.getMinute()).build())
                .build();
    }

    @Override
    public String toString() {
        return "Project: " + name + "_" + idproject + "; /n Team Assigned ID:" + teamAssigned + "; Started at " + startDate.toString() + " with deadline of " + deadline.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProjectEntity that = (ProjectEntity) o;
        return idproject == that.idproject && teamAssigned == that.teamAssigned && startDate.equals(that.startDate) && deadline.equals(that.deadline)
                && Objects.equals(name, that.name) && Objects.equals(requirements,
                that.requirements);
    }

    @Override public int hashCode()
    {
        return Objects.hash(idproject, teamAssigned, name, startDate, deadline, requirements);
    }
}
