package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.entities.MinistryEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.MinistryModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MinistryService {
    public List<MinistryModel> getAllMinistryList();
    public ApiResponseModel saveMinistry(MinistryEntity entity, MultipartFile file);
    public String getMinistryImage(String ministryName);
}
