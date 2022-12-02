package com.sep3yg9.njorddata.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity @Table(name = "logbook", schema = "sep3ygroup9") public class LogbookEntity
{
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "idlogbook", nullable = false) private Integer id;

  @ManyToOne(fetch = FetchType.LAZY) @OnDelete(action = OnDeleteAction.CASCADE) @JoinColumn(name = "assignedproject") private ProjectEntity assignedproject;

  @OneToMany(mappedBy = "assignedlogbook") private Set<LogbookentryEntity> logbookentries = new LinkedHashSet<>();

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public ProjectEntity getAssignedproject()
  {
    return assignedproject;
  }

  public void setAssignedproject(ProjectEntity assignedproject)
  {
    this.assignedproject = assignedproject;
  }

  public Set<LogbookentryEntity> getLogbookentries()
  {
    return logbookentries;
  }

  public void setLogbookentries(Set<LogbookentryEntity> logbookentries)
  {
    this.logbookentries = logbookentries;
  }

}