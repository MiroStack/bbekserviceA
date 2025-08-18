package com.bbek.BbekServiceA.model;

import lombok.Data;

@Data
public class LoginResponseModel {
    private String token;
    private String role;
    private String fullName;
    private String email;
}
