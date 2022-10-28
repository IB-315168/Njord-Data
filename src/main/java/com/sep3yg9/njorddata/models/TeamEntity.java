package com.sep3yg9.njorddata.models;

import javax.persistence.*;

@Entity @Table(name = "team", schema = "sep3ygroup9")
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idTeam;

    private int teamLeader;

    private String name;

    public TeamEntity() {

    }

    public TeamEntity(int teamLeader, String name) {
        this.teamLeader = teamLeader;
        this.name = name;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public int getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(int teamLeader) {
        this.teamLeader = teamLeader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Team [teamId=" + idTeam + ", name=" + name + "]";
    }
}
