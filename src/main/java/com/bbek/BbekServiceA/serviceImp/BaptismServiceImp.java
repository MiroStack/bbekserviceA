package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.BaptismEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.repository.BaptismRepo;
import com.bbek.BbekServiceA.service.BaptismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaptismServiceImp implements BaptismService {
    @Autowired
    BaptismRepo bRepo;
    @Override
    public ApiResponseModel submitBaptismRequest(BaptismEntity entity) {
        ApiResponseModel res = new ApiResponseModel();
        try{
            bRepo.save(entity);
            res.setMessage("Your application is successfully submitted.");
            res.setStatusCode(200);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
