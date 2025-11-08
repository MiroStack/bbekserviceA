package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.LoginResponseModel;
import com.bbek.BbekServiceA.model.RegistrationRequestModel;
import com.bbek.BbekServiceA.model.TokenModel;

public interface AuthService {
    public ApiResponseModel login(String username, String password);
    public ApiResponseModel register(RegistrationRequestModel model);
    public ApiResponseModel getUserInfo(String token);
    TokenModel getTokenModel(String token);
}
