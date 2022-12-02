package com.sep3yg9.njorddata.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity @Table(name = "logbookentry", schema = "sep3ygroup9") public class LogbookentryEntity
{
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "identry", nullable = false) private Integer id;

  @ManyToOne(fetch = FetchType.LAZY) @OnDelete(action = OnDeleteAction.CASCADE) @JoinColumn(name = "assignedlogbook") private LogbookEntity assignedlogbook;

  @ManyToOne(fetch = FetchType.LAZY) @OnDelete(action = OnDeleteAction.CASCADE) @JoinColumn(name = "assignedmeeting") private MeetingEntity assignedmeeting;

  @Column(name = "contents") @Type(type = "org.hibernate.type.TextType") private String contents;

  public LogbookentryEntity() {
  }

  public LogbookentryEntity(LogbookEntity assignedlogbook, MeetingEntity assignedmeeting, String contents) {
    this.assignedlogbook = assignedlogbook;
    this.assignedmeeting = assignedmeeting;
    this.contents = contents;
  }

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public LogbookEntity getAssignedlogbook()
  {
    return assignedlogbook;
  }

  public void setAssignedlogbook(LogbookEntity assignedlogbook)
  {
    this.assignedlogbook = assignedlogbook;
  }

  public MeetingEntity getAssignedmeeting()
  {
    return assignedmeeting;
  }

  public void setAssignedmeeting(MeetingEntity assignedmeeting)
  {
    this.assignedmeeting = assignedmeeting;
  }

  public String getContents()
  {
    return contents;
  }

  public void setContents(String contents)
  {
    this.contents = contents;
  }

}