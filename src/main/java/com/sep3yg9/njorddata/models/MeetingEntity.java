package com.sep3yg9.njorddata.models;


import com.sep3yg9.njorddata.grpc.protobuf.meeting.BasicMeeting;
import com.sep3yg9.njorddata.grpc.protobuf.meeting.MeetingGrpc;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="Meeting") @Table(name = "meeting", schema = "sep3ygroup9")
public class MeetingEntity
{
    @Id
    @GeneratedValue
    @Column(name = "idmeeting")
    private int idmeeting;

    @ManyToOne(fetch = FetchType.LAZY) @OnDelete(action = OnDeleteAction.CASCADE) @JoinColumn(name = "assignedleader") private MemberEntity assignedleader;



    private String title;
    private String description;
    private LocalDateTime startdatetime;
    private LocalDateTime enddatetime;

    @ManyToOne(fetch = FetchType.LAZY) @OnDelete(action = OnDeleteAction.CASCADE) @JoinColumn(name = "assignedproject") private ProjectEntity assignedproject;

    public ProjectEntity getAssignedproject()
    {
        return assignedproject;
    }

    public void setAssignedproject(ProjectEntity assignedproject)
    {
        this.assignedproject = assignedproject;
    }

    public MeetingEntity()
    {

    }

    public MeetingEntity(ProjectEntity project, MemberEntity assignedleader, String title, String description, LocalDateTime startdatetime, LocalDateTime enddatetime) {
        this.assignedproject = project;
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

    public MemberEntity getAssignedleader() {
        return assignedleader;
    }

    public void setAssignedleader(MemberEntity assignedleader) {
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

    public BasicMeeting convertToBasicMeeting() {
        return BasicMeeting.newBuilder()
            .setId(idmeeting)
            .setTitle(title)
            .setStartdate(SpecificDateTimeConverter.convertToSpecificDateTime(startdatetime))
            .setEnddate(SpecificDateTimeConverter.convertToSpecificDateTime(enddatetime))
            .build();
    }

    public MeetingGrpc convertToMeetingGrpc()
    {
        return MeetingGrpc.newBuilder()
                .setId(idmeeting)
                .setAssignedleader(assignedleader.convertToMemberGrpc())
                .setTitle(title)
                .setDescription(description)
                .setStartdate(SpecificDateTimeConverter.convertToSpecificDateTime(startdatetime))
                .setEnddate(SpecificDateTimeConverter.convertToSpecificDateTime(enddatetime))
                .build();
    }
}
