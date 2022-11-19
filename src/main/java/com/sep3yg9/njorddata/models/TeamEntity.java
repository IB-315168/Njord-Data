package com.sep3yg9.njorddata.models;


import com.sep3yg9.njorddata.grpc.protobuf.team.Team;
import com.sep3yg9.njorddata.grpc.protobuf.user.BasicTeam;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idteam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamMember> member = new ArrayList<>();

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
        return member;
    }

    public void setMembers(List<TeamMember> members)
    {
        this.member = members;
    }

    public void addMember(UserEntity p) {
        TeamMember teamMember = new TeamMember(this, p);
        member.add(teamMember);
        p.getTeams().add(teamMember);
    }

    public void removeMember(UserEntity p) {
        for(Iterator<TeamMember> i = member.iterator();
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

    public BasicTeam convertToBasicTeam() {
        return BasicTeam.newBuilder()
            .setId(idteam)
            .setTeamLeaderName(String.valueOf(teamleader))
            .setName(name)
            .build();
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
            && Objects.equals(name, that.name) && Objects.equals(member,
            that.member);
    }

    @Override public int hashCode()
    {
        return Objects.hash(idteam, teamleader, name, member);
    }
}
