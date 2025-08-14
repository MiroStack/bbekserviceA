package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.entities.BaptismEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;

public interface BaptismService {
    ApiResponseModel submitBaptismRequest(BaptismEntity entity);
}
