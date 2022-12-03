package com.sep3yg9.njorddata.models;

import com.sep3yg9.njorddata.grpc.protobuf.member.MemberAvailabilityGrpc;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalTime;

@Entity @Table(name = "memberavailability", schema = "sep3ygroup9") public class Memberavailability
{
  @Id @GeneratedValue @Column(name = "idavailability", nullable = false) private Integer id;

  @ManyToOne(fetch = FetchType.LAZY) @OnDelete(action = OnDeleteAction.CASCADE) @JoinColumn(name = "assignedmember") private MemberEntity assignedmember;

  @Column(name = "dayofweek", nullable = false) private Integer dayofweek;

  @Column(name = "starthour", nullable = false) private LocalTime starthour;

  @Column(name = "endhour", nullable = false) private LocalTime endhour;

  public Memberavailability()
  {
  }

  public Memberavailability(MemberEntity assignedmember, Integer dayofweek,
      LocalTime starthour, LocalTime endhour)
  {
    this.assignedmember = assignedmember;
    this.dayofweek = dayofweek;
    this.starthour = starthour;
    this.endhour = endhour;
  }

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public MemberEntity getAssignedmember()
  {
    return assignedmember;
  }

  public void setAssignedmember(MemberEntity assignedmember)
  {
    this.assignedmember = assignedmember;
  }

  public Integer getDayofweek()
  {
    return dayofweek;
  }

  public void setDayofweek(Integer dayofweek)
  {
    this.dayofweek = dayofweek;
  }

  public LocalTime getStarthour()
  {
    return starthour;
  }

  public void setStarthour(LocalTime starthour)
  {
    this.starthour = starthour;
  }

  public LocalTime getEndhour()
  {
    return endhour;
  }

  public void setEndhour(LocalTime endhour)
  {
    this.endhour = endhour;
  }

  public MemberAvailabilityGrpc convertToMemberAvailabilityGrpc() {
    return MemberAvailabilityGrpc.newBuilder()
        .setId(id)
        .setAssignedmember(getAssignedmember().getIdmember())
        .setDayofweek(dayofweek)
        .setStarthour(SpecificDateTimeConverter.convertToSpecificTime(starthour))
        .setEndhour(SpecificDateTimeConverter.convertToSpecificTime(endhour))
        .build();
  }
}