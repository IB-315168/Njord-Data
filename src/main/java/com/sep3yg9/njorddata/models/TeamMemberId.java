package com.sep3yg9.njorddata.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TeamMemberId implements Serializable
{
  @Column(name = "idteam")
  private int teamId;
  @Column(name = "idmember")
  private int memberId;

  public TeamMemberId()
  {

  }

  public TeamMemberId(int idteam, int idmember)
  {
    this.teamId = idteam;
    this.memberId = idmember;
  }

  public void setTeamId(int teamId)
  {
    this.teamId = teamId;
  }

  public void setMemberId(int memberId)
  {
    this.memberId = memberId;
  }

  public int getTeamId()
  {
    return teamId;
  }

  public int getMemberId()
  {
    return memberId;
  }

  @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    TeamMemberId that = (TeamMemberId) o;
    return teamId == that.teamId && memberId == that.memberId;
  }

  @Override public int hashCode()
  {
    return Objects.hash(teamId, memberId);
  }
}
