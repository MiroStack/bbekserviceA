package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.entities.church_information.ChurchInformationEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import org.springframework.web.multipart.MultipartFile;

public interface ChurchInformationService {
    ApiResponseModel saveChurchInformation(ChurchInformationEntity entity, MultipartFile file);
    ApiResponseModel viewChurchInformation();
    String viewLogo();
}
