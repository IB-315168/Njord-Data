package com.sep3yg9.njorddata.models;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Team") @Table(name = "team", schema = "sep3ygroup9")
public class TeamEntity
{
    @Id
    @GeneratedValue
    @Column(name = "idteam")
    private int idteam;

    private int teamleader;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "idteam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamMember> members = new ArrayList<>();

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

    public List<TeamMember> getMembers()
    {
        return members;
    }

    public void setMembers(List<TeamMember> members)
    {
        this.members = members;
    }

    public void addMember(UserEntity p) {
        TeamMember teamMember = new TeamMember(this, p);
        members.add(teamMember);
        p.getTeams().add(teamMember);
    }

    public void removeMember(UserEntity p) {
        for(Iterator<TeamMember> i = members.iterator();
        i.hasNext();) {
            TeamMember teamMember = i.next();

            if(teamMember.getTeamEntity().equals(this) &&
            teamMember.getUserEntity().equals(p)) {
                i.remove();
                teamMember.getUserEntity().getTeams().remove(teamMember);
                teamMember.setTeamEntity(null);
                teamMember.setUserEntity(null);
            }
        }
    }

    @Override
    public String toString() {
        return "Team [teamId=" + idteam + ", name=" + name + "]";
    }

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TeamEntity that = (TeamEntity) o;
        return idteam == that.idteam && teamleader == that.teamleader
            && Objects.equals(name, that.name) && Objects.equals(members,
            that.members);
    }

    @Override public int hashCode()
    {
        return Objects.hash(idteam, teamleader, name, members);
    }
}
