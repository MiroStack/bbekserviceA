package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.model.ApiResponseModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ChildDedicationService {
    ApiResponseModel listOfChildDedication(String query, int pages, String statusName);
    ApiResponseModel  requestChildDedication(Long memberId,  String guardianName, String childName, LocalDate birthdate, LocalDateTime dedicationDt, String contactNo);
    ApiResponseModel updateChildDedication(Long reqId, Long memberId,  String childName, String guardianName, LocalDate birthdate,  LocalDateTime dedicationDt, String contactNo, Long statusId);
    ApiResponseModel viewChildDedicationDetails(Long reqId);
    ApiResponseModel getKPI();
}
