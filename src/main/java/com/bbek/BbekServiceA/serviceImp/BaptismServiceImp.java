package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.BaptismEntity;
import com.bbek.BbekServiceA.entities.MemberEntity;
import com.bbek.BbekServiceA.entities.UserAccountEntity;
import com.bbek.BbekServiceA.entities.UserProfileEntity;
import com.bbek.BbekServiceA.entities.modified.baptism.ModifiedBaptismEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.RegistrationModel;
import com.bbek.BbekServiceA.model.RegistrationRequestModel;
import com.bbek.BbekServiceA.model.baptism.BaptismResponseModel;
import com.bbek.BbekServiceA.repository.BaptismRepo;
import com.bbek.BbekServiceA.repository.MemberRepo;
import com.bbek.BbekServiceA.repository.UserProfileRepo;
import com.bbek.BbekServiceA.repository.UserRepo;
import com.bbek.BbekServiceA.service.BaptismService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bbek.BbekServiceA.util.Constant.SUCCESS;

@Service
public class BaptismServiceImp implements BaptismService {
    @Autowired
    BaptismRepo bRepo;
    @Autowired
    UserProfileRepo upRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    MemberRepo mRepo;
    @Autowired
    BCryptPasswordEncoder encoder;

    private ApiResponseModel res;

    @Override
    public ApiResponseModel submitBaptismRequest(RegistrationRequestModel rModel) {
        ApiResponseModel res = new ApiResponseModel();
        try{

            UserAccountEntity uae = new UserAccountEntity();
            UserProfileEntity upe = new UserProfileEntity();
            BaptismEntity bae = new BaptismEntity();
            MemberEntity mbe = new MemberEntity();
            LocalDateTime date = LocalDateTime.now();
            int year = date.getYear();
            int month = date.getMonthValue();
            int day = date.getDayOfMonth();
            UserAccountEntity userAccountEntity = userRepo.findByUsername(rModel.getEmail());
            if(userAccountEntity != null) return new ApiResponseModel("Your email is already registered", 400,"");
            uae.setUsername(rModel.getEmail());
            uae.setPassword(encoder.encode(rModel.getFirstname()+year+month+day));
            UserAccountEntity savedUae = userRepo.save(uae);

            upe.setFirstname(rModel.getFirstname());
            upe.setMiddlename(rModel.getMiddlename());
            upe.setLastname(rModel.getLastname());
            upe.setAge(rModel.getAge());
            upe.setBirthdate(rModel.getBirthdate());
            upe.setAddress(rModel.getAddress());
            upe.setUserId(savedUae.getId());
            upe.setEmail(rModel.getEmail());
            upe.setCreatedDate(LocalDate.now().toString());
            upe.setContactNo(rModel.getContactNo());
            upe.setGender(rModel.getGender());
            upe.setRoleId(3L);

            UserProfileEntity savedUpe = upRepo.save(upe);
            bae.setPreferred_dt(rModel.getPreferred_dt());
            bae.setCreatedDate(LocalDate.now());
            bae.setProfileId(savedUpe.getId());
            bae.setStatusId(9L);
            bae.setTestimony(rModel.getTestimony());
            bRepo.save(bae);
            res.setMessage("Your application is successfully submitted.");
            res.setStatusCode(200);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel getPaginatedBaptism(String query, int page) {
        try{
            ApiResponseModel res = new ApiResponseModel();
            String queryFormatted = "%"+query+"%";
            int numberOfRowsToSkip = page == 1? 0 : (page - 1) * 10;
            List<ModifiedBaptismEntity> list = bRepo.paginatedBaptism(queryFormatted, numberOfRowsToSkip);
            List<BaptismResponseModel> data = list.stream().map(m->{
                Optional<UserProfileEntity> uaeOptional = upRepo.findById(m.getProfileId());
                UserProfileEntity uae =uaeOptional.orElse(new UserProfileEntity());
                BaptismResponseModel br = new BaptismResponseModel();
                br.setUserInfoModel(uae);
                br.setBaptismDate(m.getBaptismDate());
                br.setBaptismOfficiant(m.getBaptismOfficiant());
                br.setBaptismOfficiantId(m.getBaptismOfficiantId());
                br.setPreferred_dt(m.getPreferred_dt());
                br.setCreatedDate(m.getCreatedDate());
                br.setTestimony(m.getTestimony());
                br.setScheduledDate(m.getScheduledDate());
                br.setStatusId(m.getStatusId());
                br.setTotalRows(m.getTotalRows());
                return br;
            }).toList();
            res.setData(data);
            res.setMessage(SUCCESS);
            res.setStatusCode(200);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
