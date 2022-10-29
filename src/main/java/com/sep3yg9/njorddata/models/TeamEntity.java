package com.sep3yg9.njorddata.models;

import com.sep3yg9.njorddata.grpc.protobuf.team.User;

import javax.persistence.*;
import java.util.List;

@Entity @Table(name = "team", schema = "sep3ygroup9")
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idteam;

    private int teamleader;

    private String name;

    public TeamEntity() {

    }

    public TeamEntity(int teamLeader, String name) {
        this.teamleader = teamLeader;
        this.name = name;
    }

    public int getIdTeam() {
        return idteam;
    }

    public void setIdTeam(int idTeam) {
        this.idteam = idTeam;
    }

    public int getTeamLeader() {
        return teamleader;
    }

    public void setTeamLeader(int teamLeader) {
        this.teamleader = teamLeader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Team [teamId=" + idteam + ", name=" + name + "]";
    }
}
