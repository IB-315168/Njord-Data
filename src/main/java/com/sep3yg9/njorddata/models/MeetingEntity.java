package com.sep3yg9.njorddata.models;


import com.google.protobuf.Int32Value;
import com.google.protobuf.Timestamp;
import com.sep3yg9.njorddata.grpc.protobuf.meeting.Meeting;
import com.sep3yg9.njorddata.grpc.protobuf.project.Project;
import com.sep3yg9.njorddata.grpc.protobuf.project.Requirement;
import com.sep3yg9.njorddata.grpc.protobuf.project.SpecificTime;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity(name="Meeting") @Table(name = "meeting", schema = "sep3ygroup9")
public class MeetingEntity
{
    @Id
    @GeneratedValue
    @Column(name = "idmeeting")
    private int idmeeting;

    @ManyToOne(fetch = FetchType.LAZY) @OnDelete(action = OnDeleteAction.CASCADE) @JoinColumn(name = "assignedleader") private UserEntity assignedleader;

    private String title;
    private String description;
    private LocalDateTime startdatetime;
    private LocalDateTime enddatetime;

    public MeetingEntity()
    {

    }

    public MeetingEntity(UserEntity assignedleader, String title, String description, LocalDateTime startdatetime, LocalDateTime enddatetime) {
        this.assignedleader = assignedleader;
        this.title = title;
        this.description = description;
        this.startdatetime = startdatetime;
        this.enddatetime = enddatetime;
    }

    public int getIdmeeting() {
        return idmeeting;
    }

    public void setIdmeeting(int idmeeting) {
        this.idmeeting = idmeeting;
    }

    public UserEntity getAssignedleader() {
        return assignedleader;
    }

    public void setAssignedleader(UserEntity assignedleader) {
        this.assignedleader = assignedleader;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartdatetime() {
        return startdatetime;
    }

    public void setStartdatetime(LocalDateTime startdatetime) {
        this.startdatetime = startdatetime;
    }

    public LocalDateTime getEnddatetime() {
        return enddatetime;
    }

    public void setEnddatetime(LocalDateTime enddatetime) {
        this.enddatetime = enddatetime;
    }

    public Meeting convertToMeeting()
    {
        return Meeting.newBuilder()
                .setId(idmeeting)
                .setAssignedleader(assignedleader.getIdmember())
                .setTitle(title)
                .setDescription(description)
                .setStartdate(SpecificTimeConverter.convertToSpecificTime(startdatetime))
                .setEnddate(SpecificTimeConverter.convertToSpecificTime(enddatetime))
                .build();
    }
}
