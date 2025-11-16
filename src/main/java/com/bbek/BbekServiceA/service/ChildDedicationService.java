package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.model.ApiResponseModel;

public interface ChildDedicationService {

    ApiResponseModel requestChildDedication(Long memberId, String memberName, String childName);
    ApiResponseModel updateChildDedication();
    ApiResponseModel viewChildDedicationDetails(Long reqId);
}
