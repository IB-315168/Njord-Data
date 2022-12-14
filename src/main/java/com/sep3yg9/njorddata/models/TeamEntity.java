package com.sep3yg9.njorddata.models;


import com.sep3yg9.njorddata.grpc.protobuf.member.BasicTeam;
import com.sep3yg9.njorddata.grpc.protobuf.member.MemberGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.team.TeamGrpc;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Team") @Table(name = "team", schema = "sep3ygroup9")
public class TeamEntity
{
    @Id
    @GeneratedValue
    @Column(name = "idteam")
    private int idteam;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idteam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamMember> member = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY) @OnDelete(action = OnDeleteAction.CASCADE) @JoinColumn(name = "teamleader") private MemberEntity teamleader;

    public MemberEntity getTeamleader()
    {
        return teamleader;
    }

    public void setTeamleader(MemberEntity teamleader)
    {
        this.teamleader = teamleader;
    }

    public TeamEntity() {

    }

    public TeamEntity(MemberEntity teamLeader, String name) {
        this.teamleader = teamLeader;
        this.name = name;
    }

    public int getIdTeam() {
        return idteam;
    }

    public void setIdTeam(int idTeam) {
        this.idteam = idTeam;
    }

    public MemberEntity getTeamLeader() {
        return teamleader;
    }

    public void setTeamLeader(MemberEntity teamLeader) {
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

    public void addMember(MemberEntity p) {
        TeamMember teamMember = new TeamMember(this, p);
        member.add(teamMember);
        p.getTeams().add(teamMember);
    }

    public void removeMember(MemberEntity p) {
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
            .setTeamLeaderName(teamleader.getFullName())
            .setName(name)
            .build();
    }

    public TeamGrpc convertToTeamGrpc() {
        Set<MemberGrpc> memberEntities = new LinkedHashSet<>();

        for(TeamMember memberEntity : member) {
            memberEntities.add(memberEntity.getUserEntity()
                .convertToMemberGrpc());
        }

        return TeamGrpc.newBuilder()
            .setId(idteam)
            .setTeamLeader(teamleader.convertToMemberGrpc())
            .setName(name)
            .addAllMembers(memberEntities)
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
        return idteam == that.idteam && teamleader.equals(that.teamleader)
            && Objects.equals(name, that.name) && Objects.equals(member,
            that.member);
    }

    @Override public int hashCode()
    {
        return Objects.hash(idteam, teamleader, name, member);
    }
}
