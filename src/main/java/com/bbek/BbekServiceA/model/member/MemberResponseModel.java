package com.bbek.BbekServiceA.model.member;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberResponseModel {
    private Long id;
    private String memberName;
    private int age;
    private String address;
    private String status;
    private String joinDate;
    private boolean isActive;
    private int totalRows;
}
