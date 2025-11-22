package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.dedication.ChildDedicationEntity;
import com.bbek.BbekServiceA.entities.dedication.ChildDedicationJoinedEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.dedication.ChildDedicationKPIModel;
import com.bbek.BbekServiceA.repository.dedication.ChildDedicationRepo;
import com.bbek.BbekServiceA.service.ChildDedicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.bbek.BbekServiceA.util.Constant.SUCCESS;

@Slf4j
@Service
public class ChildDedicationServiceImp implements ChildDedicationService {
    @Autowired
    ChildDedicationRepo cdRepo;



    @Override
    public ApiResponseModel listOfChildDedication(String query, int pages, String statusName) {
        try{
            int numberOfRowsToSkip = pages == 1 ? 0 : (pages - 1) * 10;
            List<ChildDedicationJoinedEntity> list = cdRepo.fetchChildDedicationDetails(query, numberOfRowsToSkip, statusName);
            return new ApiResponseModel("Dedication record fetched successfully", 200, list);
        } catch (Exception e) {
            log.error("Message", e);
            return new ApiResponseModel("Failed to fetch list of child dedication", 500, null);
        }
    }

    @Override
    @Transactional
    public ApiResponseModel requestChildDedication(Long memberId,  String childName, String guardianName, LocalDate birthdate,  LocalDateTime dedicationDt, String contactNo) {
        try{
            ChildDedicationEntity entity = new ChildDedicationEntity();
            entity.setChildName(childName);
            entity.setBirthDate(birthdate);
            entity.setGuardianName(guardianName);
            entity.setStatus(1L);
            entity.setDedicationDt(dedicationDt);
            entity.setContactNo(contactNo);
            entity.setCreatedDt(LocalDateTime.now());
            entity.setCreatedById(memberId);
            cdRepo.save(entity);
            return new ApiResponseModel("Request for child dedication has been successfully submitted.", 200, null);

        } catch (Exception e) {
            log.error("Message", e);
            return new ApiResponseModel("Failed to submit the request. Please try again.", 500, null);
        }

    }

    @Override
    @Transactional
    public ApiResponseModel updateChildDedication(Long reqId, Long memberId,  String childName, String guardianName, LocalDate birthdate,  LocalDateTime dedicationDt, String contactNo, Long statusId) {
        try{
            Optional<ChildDedicationEntity> oce = cdRepo.findById(reqId);
            ChildDedicationEntity entity = oce.orElse(null);
            if(entity == null) return new ApiResponseModel("Child dedication request not found.", 404, null);
            entity.setChildName(childName);
            entity.setBirthDate(birthdate);
            entity.setGuardianName(guardianName);
            entity.setStatus(statusId);
            entity.setDedicationDt(dedicationDt);
            entity.setContactNo(contactNo);
            entity.setModifiedDt(LocalDateTime.now());
            entity.setModifiedById(memberId);
            cdRepo.save(entity);
            return new ApiResponseModel("Request for child dedication has been successfully updated.", 200, null);

        } catch (Exception e) {
            log.error("Message", e);
            return new ApiResponseModel("Failed to update the request. Please try again.", 500, null);
        }
    }

    @Override
    public ApiResponseModel viewChildDedicationDetails(Long reqId) {

        return null;
    }

    @Override
    public ApiResponseModel getKPI() {
        Long totalRows = cdRepo.getTotalDedications();
        Long completedDedication = cdRepo.getTotalCompletedDedication();
        Long newReqDedication = cdRepo.getTotalNewDedication();
        ChildDedicationKPIModel kpiModel = new ChildDedicationKPIModel(
                totalRows,
                completedDedication,
                newReqDedication
        );
        return  new ApiResponseModel(SUCCESS, 200, kpiModel);
    }
}
