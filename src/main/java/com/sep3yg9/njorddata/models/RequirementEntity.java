package com.sep3yg9.njorddata.models;

import com.sep3yg9.njorddata.grpc.protobuf.project.Requirement;
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

    public RequirementEntity()
    {
    }

    public RequirementEntity(ProjectEntity idproject,
        String requirement)
    {
        this.idproject = idproject;
        this.requirement = requirement;
    }

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

    public Requirement convertToRequirement() {
        return Requirement.newBuilder()
            .setId(idrequirement)
            .setIdproject(idproject.getIdproject())
            .setContent(requirement)
            .build();
    }
}
