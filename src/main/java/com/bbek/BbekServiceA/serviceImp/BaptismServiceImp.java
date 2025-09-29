package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.BaptismEntity;
import com.bbek.BbekServiceA.entities.UserModel;
import com.bbek.BbekServiceA.entities.UserProfileModel;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.RegistrationModel;
import com.bbek.BbekServiceA.model.RegistrationRequestModel;
import com.bbek.BbekServiceA.repository.BaptismRepo;
import com.bbek.BbekServiceA.repository.UserProfileRepo;
import com.bbek.BbekServiceA.repository.UserRepo;
import com.bbek.BbekServiceA.service.AuthService;
import com.bbek.BbekServiceA.service.BaptismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;

@Service
public class BaptismServiceImp implements BaptismService {
    @Autowired
    BaptismRepo bRepo;
    @Autowired
    UserProfileRepo upRepo;
    @Autowired
    UserRepo userRepo;
    @Override
    public ApiResponseModel submitBaptismRequest(RegistrationModel model) {
        ApiResponseModel res = new ApiResponseModel();
        try{

            UserModel userModel = new UserModel();
            UserProfileModel userProfileModel = new UserProfileModel();
            RegistrationRequestModel rModel = model.getRegistrationRequestModel();
            BaptismEntity entity = model.getBaptismEntity();
            LocalDateTime date = LocalDateTime.now();
            int year = date.getYear();
            int month = date.getMonthValue();
            int day = date.getDayOfMonth();
            rModel.setPassword(rModel.getFirstname() + year + month + day);
            rModel.setUsername(rModel.getEmail());




            entity.setStatusId(2L);
            bRepo.save(entity);
            res.setMessage("Your application is successfully submitted.");
            res.setStatusCode(200);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel getPaginatedBaptism(String query, int page) {
        return null;
    }
}
