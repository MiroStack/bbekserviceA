package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.reference.ServiceStatusEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.repository.reference.ServiceStatusRepo;
import com.bbek.BbekServiceA.service.RefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bbek.BbekServiceA.util.Constant.SUCCESS;

@Service
public class RefServiceImp implements RefService {
    @Autowired
    ServiceStatusRepo serviceStatusRepo;

    @Override
    public ApiResponseModel findAllServiceStatus() {
        List<ServiceStatusEntity> entities = serviceStatusRepo.findAll();
        return new ApiResponseModel(SUCCESS, 200, entities);
    }
}
