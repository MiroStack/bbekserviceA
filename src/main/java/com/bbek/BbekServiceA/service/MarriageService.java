package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.entities.MarriageEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;

public interface MarriageService {
    ApiResponseModel getAllMarriages(String query, int page);
    ApiResponseModel saveMarriages(MarriageEntity entity, boolean isUpdate);
    ApiResponseModel deleteMarriage(Long id);
    ApiResponseModel getAllMarriageStatuses();
    ApiResponseModel getAllLocations();

}
