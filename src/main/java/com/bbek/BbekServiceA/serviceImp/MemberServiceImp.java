package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.BaptismEntity;
import com.bbek.BbekServiceA.entities.UserAccountEntity;
import com.bbek.BbekServiceA.entities.UserProfileEntity;
import com.bbek.BbekServiceA.entities.modified.member.ModifiedMemberEntity;
import com.bbek.BbekServiceA.entities.reference.StatusEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.baptism.AddBaptismRequestModel;
import com.bbek.BbekServiceA.model.member.MemberResponseModel;
import com.bbek.BbekServiceA.repository.BaptismRepo;
import com.bbek.BbekServiceA.repository.MemberRepo;
import com.bbek.BbekServiceA.repository.UserProfileRepo;
import com.bbek.BbekServiceA.repository.UserRepo;
import com.bbek.BbekServiceA.repository.reference.CertificateStatusRepo;
import com.bbek.BbekServiceA.repository.reference.RoleRepo;
import com.bbek.BbekServiceA.repository.reference.StatusRepo;
import com.bbek.BbekServiceA.service.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.bbek.BbekServiceA.util.Constant.SUCCESS;

@Service
public class MemberServiceImp implements MemberService {
    @Autowired
    MemberRepo memberRepo;
    @Autowired
    BaptismRepo bRepo;
    @Autowired
    UserProfileRepo upRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    MemberRepo mRepo;
    @Autowired
    RoleRepo rRepo;
    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    StatusRepo stRepo;
    @Autowired
    CertificateStatusRepo ctRepo;
    @Autowired
    EmailSenderServiceImp emailSenderServiceImp;
    @Autowired
    StatusRepo statusRepo;

    @Override
    public ApiResponseModel getMemberPage(String query, int page) {
        ApiResponseModel res = new ApiResponseModel();
        try {
            String queryFormatted = "%" + query + "%";
            int numberOfRowsToSkip = page == 1 ? 0 : (page - 1) * 10;
            List<ModifiedMemberEntity> list = memberRepo.getMembers(queryFormatted, numberOfRowsToSkip);
            List<MemberResponseModel> data = list.stream().map(m -> {

                Optional<UserProfileEntity> upo = upRepo.findById(m.getProfileId());
                UserProfileEntity upe = upo.orElse(null);
                Optional<StatusEntity> sto = statusRepo.findById(m.getStatusId());
                StatusEntity ste = sto.orElse(null);
                MemberResponseModel mrm = new MemberResponseModel(
                        m.getId(),
                        m.getMemberName(),
                        upe.getAge(),
                        upe.getAddress(),
                        ste.getStatusName(),
                        m.getJoinDate(),
                        m.isActive(),
                        m.getTotalRows());


                return mrm;
            }).toList();
            res.setMessage(SUCCESS);
            res.setData(data);
            res.setStatusCode(200);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public ApiResponseModel addMember(AddBaptismRequestModel model) {
        ApiResponseModel res=new ApiResponseModel();
        try{
            UserAccountEntity uae = new UserAccountEntity();
            UserProfileEntity upe = new UserProfileEntity();
            BaptismEntity be = new BaptismEntity();

            UserAccountEntity checkEntity = userRepo.findByUsername(model.getEmail());
            if(checkEntity != null)return new ApiResponseModel("Email is already used.", 400, "");
            LocalDateTime dateTime = LocalDateTime.now();
            int year = dateTime.getYear();
            int month = dateTime.getMonthValue();
            int day = dateTime.getDayOfMonth();
            String password = model.getFirstname()+year+month+day;
            //saving of account credentials
            uae.setUsername(model.getEmail());
            uae.setPassword(encoder.encode(password));
            UserAccountEntity savedUAE = userRepo.save(uae);
            //saving of account profiles
            upe.setFirstname(model.getFirstname());
            upe.setMiddlename(model.getMiddlename());
            upe.setLastname(model.getLastname());
            upe.setAge(model.getAge());
            upe.setGender(model.getSex());
            upe.setAddress(model.getAddress());
            upe.setEmail(model.getEmail());
            upe.setContactNo(model.getContactNo());
            upe.setBirthdate(model.getBirthdate());
            upe.setRoleId(1L);
            upe.setUserId(savedUAE.getId());
            UserProfileEntity savedUPE = upRepo.save(upe);
            //creating baptism schedule
            be.setBaptismDate(model.getBaptismDate());
            be.setBaptismOfficiant(model.getBaptismOfficiant());
            be.setBaptismOfficiantId(model.getBaptismOfficiantId());
            be.setLocation(model.getLocation());
            be.setProfileId(savedUPE.getId());
            be.setCertificateStatus(2L);
            be.setStatusId(8L);
            be.setLocation(model.getLocation());


            bRepo.save(be);

            String message = "Dear "+upe.getFirstname() +" "+upe.getLastname()+" ,\n" +
                    "\n" +
                    "We are overjoyed to welcome you as an official member of Bible Baptist of Eklessia! Your decision to become part of our community is truly a blessing, and we thank God for guiding you to our family of faith.\n" +
                    "\n" +
                    "As a member, you are now part of a loving and supportive community that seeks to grow together in faith, serve others with compassion, and share the message of God’s love. We encourage you to join our worship services, ministry activities, and fellowship gatherings, where you can deepen your connection with God and with your fellow believers.\n" +
                    "We also generate an account for you so that you can login in our website and view upcoming events or become part of our ministries activities. \n" +
                    "Username: "+uae.getUsername()+"\n"+
                    "Password: "+password+"\n"+
                    "If you have any questions or would like to get involved in our ministries, please don’t hesitate to reach out to us. We look forward to walking alongside you in your spiritual journey.\n" +
                    "\n" +
                    "Once again, welcome to the family — we’re so glad you’re here!\n" +
                    "\n" +
                    "With blessings and joy,\n" +
                    "Staff Admin\n" +
                    "BIBLE BAPTIST OF EKLESSIA\n" +
                    "(046) 123-4567 /  info@bbekawit.org\n"+
                    "485 Acacia St. Villa Ramirez Tabon 1, Kawit Cavite";

            emailSenderServiceImp.sendEmailMessage(model.getEmail(), message, "BAPTISM APPLICATION");
            res.setStatusCode(200);
            res.setMessage(SUCCESS);
            return res;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
