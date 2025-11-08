package com.bbek.BbekServiceA.entities.pivot;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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


}
