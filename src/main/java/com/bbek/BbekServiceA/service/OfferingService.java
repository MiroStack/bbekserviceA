package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.OfferingModel;

public interface OfferingService {
    ApiResponseModel getAllOffering();
    ApiResponseModel submitOffering(OfferingModel offeringModel, boolean isUpdate);
    ApiResponseModel updateOfferingDetails();
    ApiResponseModel getOffering(Long id);
    ApiResponseModel deleteOffering(Long id);
    ApiResponseModel getAllPaymentTypeRf();
    ApiResponseModel getAllOfferingTypeRf();
}
