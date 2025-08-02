package com.bbek.BbekServiceA.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MinistryModel {
    private long id;

    private String schedule;

    private String leader;

    private String statusName;

    private String ministryName;

    private String description;

    private int member;

    private String createdDate;

    private String updatedDate;

    public MinistryModel(long id, String schedule, String leader, String statusName, String ministryName, String description, int member, String createdDate, String updatedDate) {
        this.id = id;
        this.schedule = schedule;
        this.leader = leader;
        this.statusName = statusName;
        this.ministryName = ministryName;
        this.description = description;
        this.member = member;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
