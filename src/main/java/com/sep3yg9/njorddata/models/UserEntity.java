package com.sep3yg9.njorddata.models;

import com.sep3yg9.njorddata.grpc.protobuf.user.BasicTeam;
import com.sep3yg9.njorddata.grpc.protobuf.user.User;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.util.*;

@Entity @Table(name = "member", schema = "sep3ygroup9")
@NaturalIdCache
@org.hibernate.annotations.Cache(
    usage = CacheConcurrencyStrategy.READ_WRITE
)
public class UserEntity
{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "idmember")
  private int idmember;
  private String fullname;
  private String email;
  private String username;
  private String password;
  private String recurringavailability;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "idmember", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TeamMember> teams = new ArrayList<>();
  public UserEntity() {

  }

  public UserEntity(String fullName, String email, String userName, String password) {
    this.fullname = fullName;
    this.email = email;
    this.username = userName;
    this.password = password;
    recurringavailability = "test";
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

  @Override
  public String toString() {
    return "User [fullName=" + fullname + ", email=" + email + "]";
  }

  public User convertToUser() {
    List<BasicTeam> user = new ArrayList<>();
    for(TeamMember teamMember : teams) {
      user.add(teamMember.getTeamEntity().convertToBasicTeam());
    }
    return User.newBuilder()
        .setId(idmember)
        .setFullName(fullname)
        .setEmail(email)
        .setUserName(username)
        .setPassword(password)
        .addAllUserTeams(user)
        .build();
  }

  @Override public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserEntity that = (UserEntity) o;
    return idmember == that.idmember && Objects.equals(fullname, that.fullname)
        && Objects.equals(email, that.email) && Objects.equals(username,
        that.username) && Objects.equals(password, that.password)
        && Objects.equals(recurringavailability, that.recurringavailability)
        && Objects.equals(teams, that.teams);
  }

  @Override public int hashCode()
  {
    return Objects.hash(idmember, fullname, email, username, password,
        recurringavailability, teams);
  }
}
