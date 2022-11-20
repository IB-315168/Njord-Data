package com.sep3yg9.njorddata.models;

import javax.persistence.*;

@Entity(name = "Requirement") @Table(name = "requirement", schema = "sep3ygroup9")
public class RequirementEntity {
    @Id
    @GeneratedValue
    @Column(name="idrequirement") private int idrequirement;
}
