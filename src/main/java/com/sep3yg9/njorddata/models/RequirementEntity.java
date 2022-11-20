package com.sep3yg9.njorddata.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity(name = "Requirement") @Table(name = "requirements", schema = "sep3ygroup9")
public class RequirementEntity {
    @Id
    @GeneratedValue
    @Column(name="idrequirement") private int idrequirement;

    @ManyToOne(fetch = FetchType.LAZY) @OnDelete(action = OnDeleteAction.CASCADE) @JoinColumn(name = "idproject") private ProjectEntity idproject;

    @Column(name = "requirement", nullable = false, length = 400) private String requirement;

    public String getRequirement()
    {
        return requirement;
    }

    public void setRequirement(String requirement)
    {
        this.requirement = requirement;
    }

    public ProjectEntity getIdproject()
    {
        return idproject;
    }

    public void setIdproject(ProjectEntity idproject)
    {
        this.idproject = idproject;
    }
}
