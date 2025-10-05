package com.bbek.BbekServiceA.entities.modified.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tbl_member")
@AllArgsConstructor
@NoArgsConstructor
public class ModifiedMemberEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_name")
    private String memberName;

    @Column(name="status_id")
    private Long statusId;

    @Column(name = "profile_id")
    private Long profileId;

    @Column(name="join_date")
    private String joinDate;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name="totalRows")
    private int totalRows;
}
