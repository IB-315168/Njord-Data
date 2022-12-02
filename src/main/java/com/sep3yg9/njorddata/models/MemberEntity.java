package com.sep3yg9.njorddata.models;

import com.sep3yg9.njorddata.grpc.protobuf.member.MemberAvailabilityGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.member.MemberGrpc;
import com.sep3yg9.njorddata.grpc.protobuf.member.BasicTeam;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.util.*;

@Entity @Table(name = "member", schema = "sep3ygroup9")
@NaturalIdCache
@org.hibernate.annotations.Cache(
    usage = CacheConcurrencyStrategy.READ_WRITE
)
public class MemberEntity
{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "idmember")
  private int idmember;
  private String fullname;
  private String email;
  private String username;
  private String password;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "idmember", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TeamMember> teams = new ArrayList<>();

  @OneToMany(mappedBy = "assignedmember") private Set<Memberavailability> memberavailabilities = new LinkedHashSet<>();

  public Set<Memberavailability> getMemberavailabilities()
  {
    return memberavailabilities;
  }

  public void setMemberavailabilities(
      Set<Memberavailability> memberavailabilities)
  {
    this.memberavailabilities = memberavailabilities;
  }

  public MemberEntity() {

  }

  public MemberEntity(String fullName, String email, String userName, String password) {
    this.fullname = fullName;
    this.email = email;
    this.username = userName;
    this.password = password;
  }

  public int getIdmember()
  {
    return idmember;
  }

  public void setIdmember(int id)
  {
    this.idmember = id;
  }

  public String getFullName()
  {
    return fullname;
  }

  public void setFullName(String fullName)
  {
    this.fullname = fullName;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getUserName()
  {
    return username;
  }

  public void setUserName(String userName)
  {
    this.username = userName;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public List<TeamMember> getTeams()
  {
    return teams;
  }

  public void setTeams(List<TeamMember> teams)
  {
    this.teams = teams;
  }

  public void addAvailability(Memberavailability memberavailability) {
    memberavailabilities.add(memberavailability);
    memberavailability.setAssignedmember(this);
  }

  public void removeAvailability(Memberavailability memberavailability) {
    memberavailabilities.remove(memberavailability);
    memberavailability.setAssignedmember(null);
  }

  @Override
  public String toString() {
    return "User [fullName=" + fullname + ", email=" + email + "]";
  }

  public MemberGrpc convertToMemberGrpc() {
    List<BasicTeam> teams1 = new ArrayList<>();
    for(TeamMember teamMember : teams) {
      teams1.add(teamMember.getTeamEntity().convertToBasicTeam());
    }

    Set<MemberAvailabilityGrpc> memberavailabilitySet = new LinkedHashSet<>();
    for(Memberavailability memberavailability : memberavailabilities) {
      memberavailabilitySet.add(memberavailability.convertToMemberAvailabilityGrpc());
    }

    return MemberGrpc.newBuilder()
        .setId(idmember)
        .setFullName(fullname)
        .setEmail(email)
        .setUserName(username)
        .setPassword(password)
        .addAllMemberTeams(teams1)
        .addAllAvailability(memberavailabilitySet)
        .build();
  }

  @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    MemberEntity that = (MemberEntity) o;
    return idmember == that.idmember && Objects.equals(fullname, that.fullname)
        && Objects.equals(email, that.email) && Objects.equals(username,
        that.username) && Objects.equals(password, that.password)
        && Objects.equals(teams, that.teams);
  }

  @Override public int hashCode()
  {
    return Objects.hash(idmember, fullname, email, username, password,
        memberavailabilities, teams);
  }
}
