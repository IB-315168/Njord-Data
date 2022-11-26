package com.sep3yg9.njorddata.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "teammember", schema = "sep3ygroup9")
public class TeamMember
{
  @EmbeddedId
  private TeamMemberId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("teamId")
  private TeamEntity idteam;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("memberId")
  private MemberEntity idmember;

  public TeamMember() {

  }

  public TeamMember(TeamEntity teamEntity, MemberEntity memberEntity)
  {
    this.idteam = teamEntity;
    this.idmember = memberEntity;
    this.id = new TeamMemberId(teamEntity.getIdTeam(), memberEntity.getIdmember());
  }

  public TeamMemberId getId()
  {
    return id;
  }

  public void setId(TeamMemberId id)
  {
    this.id = id;
  }

  public TeamEntity getTeamEntity()
  {
    return idteam;
  }

  public void setTeamEntity(TeamEntity teamEntity)
  {
    this.idteam = teamEntity;
  }

  public MemberEntity getUserEntity()
  {
    return idmember;
  }

  public void setUserEntity(MemberEntity memberEntity)
  {
    this.idmember = memberEntity;
  }

  @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    TeamMember that = (TeamMember) o;
    return Objects.equals(id, that.id) && Objects.equals(idteam,
        that.idteam) && Objects.equals(idmember, that.idmember);
  }

  @Override public int hashCode()
  {
    return Objects.hash(id, idteam, idmember);
  }
}
