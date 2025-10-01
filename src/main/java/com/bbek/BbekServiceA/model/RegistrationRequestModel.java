package com.bbek.BbekServiceA.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestModel {
  private String firstname;
  private String middlename;
  private String lastname;
  private String address;
  private int age;
  private String birthdate;
  private String email;
  private String contactNo;
  private String gender;
  private LocalDate preferred_dt;
  private String testimony;
}
