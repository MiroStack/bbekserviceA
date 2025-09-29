package com.bbek.BbekServiceA.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestModel {
  private String username;
  private String password;
  private String rolename;
  private String firstname;
  private String middlename;
  private String lastname;
  private String address;
  private int age;
  private String birthdate;
  private String email;
  private String created_dt;
}
