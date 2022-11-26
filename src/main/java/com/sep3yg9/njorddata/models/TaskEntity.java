package com.sep3yg9.njorddata.models;

import com.sep3yg9.njorddata.grpc.protobuf.task.TaskGrpc;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity(name="Task") @Table(name="task", schema = "sep3ygroup9")
public class TaskEntity {
    @Id
    @GeneratedValue
    @Column(name="idtask")
    private int idtask;

    @ManyToOne(fetch = FetchType.LAZY) @OnDelete(action = OnDeleteAction.NO_ACTION) @JoinColumn(name="memberassigned")
    private MemberEntity memberassigned;

    //Need calendar entity here when its later added

    private String title;
    private String description;
    private char status;
    private LocalTime timeestimation;
    private LocalDateTime creationdate;

    public TaskEntity()
    {

    };

    public TaskEntity(MemberEntity memberassigned, String title, String description, char status, LocalTime timeestimation, LocalDateTime creationdate) {
        this.memberassigned = memberassigned;
        this.title = title;
        this.description = description;
        this.status = status;
        this.timeestimation = timeestimation;
        this.creationdate = creationdate;
    }

    public int getIdtask() {
        return idtask;
    }

    public void setIdtask(int idtask) {
        this.idtask = idtask;
    }

    public MemberEntity getMemberassigned() {
        return memberassigned;
    }

    public void setMemberassigned(MemberEntity memberassigned) {
        this.memberassigned = memberassigned;
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

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public LocalTime getTimeestimation() {
        return timeestimation;
    }

    public void setTimeestimation(LocalTime timeestimation) {
        this.timeestimation = timeestimation;
    }

    public LocalDateTime getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(LocalDateTime creationdate) {
        this.creationdate = creationdate;
    }

    private String getStatusAsString(){
        return status + "";
    }

    public TaskGrpc convertToTaskGrpc()
    {
        return TaskGrpc.newBuilder()
                .setId(idtask)
                .setMemberassigned(memberassigned.getIdmember())
                .setTitle(title)
                .setDescription(description)
                .setStatus(getStatusAsString())
                .setTimeestimation(SpecificDateTimeConverter.convertToSpecificTime(timeestimation))
                .setCreationdate(SpecificDateTimeConverter.convertToSpecificDateTime(creationdate))
                .build();
    }
}
