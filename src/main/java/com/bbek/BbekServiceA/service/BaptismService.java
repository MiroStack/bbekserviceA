package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.entities.BaptismEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.RegistrationModel;
import com.bbek.BbekServiceA.model.RegistrationRequestModel;

public interface BaptismService {
    ApiResponseModel submitBaptismRequest(RegistrationRequestModel rModel);
    ApiResponseModel getPaginatedBaptism(String query, int page);
}
