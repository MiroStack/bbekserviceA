package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.LoginResponseModel;
import com.bbek.BbekServiceA.model.RegistrationRequestModel;

public interface AuthService {
    public ApiResponseModel login(String username, String password);
    public ApiResponseModel register(RegistrationRequestModel model);
}
