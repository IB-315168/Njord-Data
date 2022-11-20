package com.sep3yg9.njorddata.models;

import com.google.type.DateTime;
import com.sep3yg9.njorddata.grpc.protobuf.project.Project;
import com.sep3yg9.njorddata.grpc.protobuf.user.BasicTeam;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Project") @Table(name = "project", schema = "sep3ygroup9")
public class ProjectEntity
{
    @Id @GeneratedValue @Column(name="idproject") private int idproject;

    private int teamAssigned;
    private String name;
    private DateTime startDate;
    private DateTime deadline;

    public ProjectEntity(){
    }

    public ProjectEntity(int teamAssigned, String name, DateTime starDate, DateTime deadline){
        this.teamAssigned = teamAssigned;
        this.name = name;
        this.startDate = starDate;
        this.deadline = deadline;
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

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(DateTime deadline) {
        this.deadline = deadline;
    }

    public Project convertToProject() {
        return Project.newBuilder()
                .setId(idproject)
                .setName(name)
                .setDeadline()
                .build();
    }
}
