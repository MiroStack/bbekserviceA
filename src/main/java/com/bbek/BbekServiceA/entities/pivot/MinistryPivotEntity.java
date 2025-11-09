package com.bbek.BbekServiceA.entities.pivot;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ministry_pivot_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MinistryPivotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ministry_id")
    private Long ministryId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "created_dt")
    private LocalDateTime createdDt;

    @Column(name = "modified_dt")
    private LocalDateTime modifiedDt;

    @Column(name = "modified_by")
    private Long modifiedBy;


}
