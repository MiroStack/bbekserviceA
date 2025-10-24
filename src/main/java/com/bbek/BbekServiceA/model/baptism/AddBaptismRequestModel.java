package com.bbek.BbekServiceA.model.baptism;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBaptismRequestModel {
    private String firstname;
    private String middlename;
    private String lastname;
    private String birthdate;
    private int age;
    private String sex;
    private String address;
    private String email;
    private String contactNo;
    private LocalDateTime baptismDate;
    private String baptismOfficiant;
    private Long baptismOfficiantId;
    private String location;
    private String baptismStringDate;
}
