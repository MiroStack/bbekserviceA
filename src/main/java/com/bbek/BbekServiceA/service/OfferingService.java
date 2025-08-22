package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.OfferingModel;

public interface OfferingService {
    ApiResponseModel getAllOffering();
    ApiResponseModel submitOffering(OfferingModel offeringModel);
    ApiResponseModel updateOfferingDetails();
    ApiResponseModel getOffering(Long id);
    ApiResponseModel deleteOffering(Long id);
}
