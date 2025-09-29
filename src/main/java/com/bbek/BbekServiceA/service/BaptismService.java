package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.entities.BaptismEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.RegistrationModel;

public interface BaptismService {
    ApiResponseModel submitBaptismRequest(RegistrationModel model);
    ApiResponseModel getPaginatedBaptism(String query, int page);
}
