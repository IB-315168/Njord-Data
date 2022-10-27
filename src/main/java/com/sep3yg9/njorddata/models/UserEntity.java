package com.sep3yg9.njorddata.models;

import javax.persistence.*;

@Entity @Table(name = "member", schema = "sep3ygroup9")
public class UserEntity
{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int idmember;
  private String fullname;
  private String email;
  private String username;
  private String password;
  private String recurringavailability;
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

  @Override
  public String toString() {
    return "User [fullName=" + fullname + ", email=" + email + "]";
  }
}
