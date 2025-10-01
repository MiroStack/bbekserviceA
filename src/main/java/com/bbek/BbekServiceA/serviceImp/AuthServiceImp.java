package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.UserAccountEntity;
import com.bbek.BbekServiceA.entities.UserProfileEntity;
import com.bbek.BbekServiceA.entities.reference.RoleEntity;
import com.bbek.BbekServiceA.model.*;
import com.bbek.BbekServiceA.repository.UserProfileRepo;
import com.bbek.BbekServiceA.repository.UserRepo;
import com.bbek.BbekServiceA.repository.reference.RoleRepo;
import com.bbek.BbekServiceA.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.bbek.BbekServiceA.util.Constant.SUCCESS;
import static com.bbek.BbekServiceA.util.Constant.USER_NOT_FOUND;

@Service
public class AuthServiceImp implements AuthService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserProfileRepo profileRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @Override
    public ApiResponseModel login(String username, String password) {
        try {
            UserAccountEntity user = userRepo.findByUsername(username);
            if(user == null)return new ApiResponseModel("Invalid username.", 404, "");
            ApiResponseModel res = new ApiResponseModel();
            if (!encoder.matches(password, user.getPassword())) {
                res.setMessage(USER_NOT_FOUND);
                res.setStatusCode(404);
                return res;
            } else {
                UserProfileEntity upe = profileRepo.findByUserId(user.getId());
                Optional<RoleEntity> roleEntityOptional = roleRepo.findById(upe.getRoleId());
                RoleEntity roleEntity = roleEntityOptional.orElse(null);
                if (roleEntity == null) return new ApiResponseModel("Invalid role id!", 404, "");

                if (roleEntity.getId() == 3) return new ApiResponseModel("Your account is for approval.", 403, "");

                TokenModel tokenModel = new TokenModel();
                tokenModel.setUsername(username);
                String token = verify(tokenModel, password);
                LoginResponseModel model = new LoginResponseModel();
                model.setToken(token);
                res.setStatusCode(200);
                res.setMessage(SUCCESS);
                res.setData(model);
                return res;


            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel register(RegistrationRequestModel model) {
        return null;
    }

    //    @Override
//    public ApiResponseModel register(RegistrationRequestModel model) {
//        ApiResponseModel res = new ApiResponseModel();
//        UserAccountEntity userModel = new UserAccountEntity();
//        UserAccountEntity checkModel = userRepo.findByUsername(model.getUsername());
//        UserProfileEntity profileModel = new UserProfileEntity();
//        try{
//            if(checkModel != null){
//                res.setMessage("Username already exist!");
//                res.setStatusCode(400);
//                return res;
//            }
//            if(model.getUsername().isEmpty() || model.getPassword().isEmpty()){
//                res.setMessage("Invalid username or password. Please try again!");
//                res.setStatusCode(400);
//                return res;
//            }
//            userModel.setUsername(model.getUsername());
//            userModel.setPassword(encoder.encode(model.getPassword()));
//            switch(model.getRolename()){
//                case "ADMIN":
//                    userModel.setRole_id(2);
//                    break;
//                case "MEMBER":
//                    userModel.setRole_id(1);
//                    break;
//                case "APPLICANT":
//                    userModel.setRole_id(3);
//                    break;
//                case "LEADER":
//                    userModel.setRole_id(4);
//                    break;
//                case "PASTOR":
//                    userModel.setRole_id(5);
//                    break;
//                case "STAFF":
//                    userModel.setRole_id(6);
//                    break;
//            }
//            userModel.setStatus_id(8);
//            UserAccountEntity saveAccount = userRepo.save(userModel);
//            //profile
//            profileModel.setFirstname(model.getFirstname());
//            profileModel.setMiddlename(model.getMiddlename());
//            profileModel.setLastname(model.getLastname());
//            profileModel.setUserId(saveAccount.getId());
//            profileModel.setAge(model.getAge());
//            profileModel.setEmail(model.getEmail());
//            profileModel.setAddress(model.getAddress());
//            profileModel.setBirthdate(model.getBirthdate());
//            profileModel.setCreatedDate(model.getCreated_dt());
//            profileRepo.save(profileModel);
//            res.setStatusCode(201);
//            res.setMessage(SUCCESS);
//            return res;
//        } catch (RuntimeException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public String verify(TokenModel model, String password) {
        Authentication authentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(model.getUsername(), password));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(model);
        } else {
            return "Incorrect username or password.";
        }
    }

}
