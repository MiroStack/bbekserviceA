package com.bbek.BbekServiceA.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class UserInfoModel {
    private String firstname;
    private String middlename;
    private String lastname;
    private int age;
    private String birthdate;
    private String address;
    private String email;
    private String createdDate;
    private String contactNo;
    private String emergencyContactPerson;
    private String emergencyContactNo;
    private String gender;
    private String imageUUID;
    private String role;
}
