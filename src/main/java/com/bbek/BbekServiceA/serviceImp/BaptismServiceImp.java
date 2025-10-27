package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.BaptismEntity;
import com.bbek.BbekServiceA.entities.MemberEntity;
import com.bbek.BbekServiceA.entities.UserAccountEntity;
import com.bbek.BbekServiceA.entities.UserProfileEntity;
import com.bbek.BbekServiceA.entities.modified.baptism.ModifiedBaptismEntity;
import com.bbek.BbekServiceA.entities.reference.CertificateStatusEntity;
import com.bbek.BbekServiceA.entities.reference.RoleEntity;
import com.bbek.BbekServiceA.entities.reference.StatusEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.RegistrationModel;
import com.bbek.BbekServiceA.model.RegistrationRequestModel;
import com.bbek.BbekServiceA.model.UserInfoModel;
import com.bbek.BbekServiceA.model.baptism.AddBaptismRequestModel;
import com.bbek.BbekServiceA.model.baptism.BaptismResponseModel;
import com.bbek.BbekServiceA.model.baptism.BaptismScheduleModel;
import com.bbek.BbekServiceA.model.user.UserAndIdModel;
import com.bbek.BbekServiceA.repository.BaptismRepo;
import com.bbek.BbekServiceA.repository.MemberRepo;
import com.bbek.BbekServiceA.repository.UserProfileRepo;
import com.bbek.BbekServiceA.repository.UserRepo;
import com.bbek.BbekServiceA.repository.reference.CertificateStatusRepo;
import com.bbek.BbekServiceA.repository.reference.RoleRepo;
import com.bbek.BbekServiceA.repository.reference.StatusRepo;
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
    RoleRepo rRepo;
    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    StatusRepo stRepo;
    @Autowired
    CertificateStatusRepo ctRepo;
    @Autowired
    EmailSenderServiceImp emailSenderServiceImp;


    @Override
    public ApiResponseModel submitBaptismRequest(RegistrationRequestModel rModel) {
        ApiResponseModel res = new ApiResponseModel();
        try {

            UserAccountEntity uae = new UserAccountEntity();
            UserProfileEntity upe = new UserProfileEntity();
            BaptismEntity bae = new BaptismEntity();
            MemberEntity mbe = new MemberEntity();
            LocalDateTime date = LocalDateTime.now();
            int year = date.getYear();
            int month = date.getMonthValue();
            int day = date.getDayOfMonth();
            UserAccountEntity userAccountEntity = userRepo.findByUsername(rModel.getEmail());
            if (userAccountEntity != null) return new ApiResponseModel("Your email is already registered", 400, "");
            uae.setUsername(rModel.getEmail());
            uae.setPassword(encoder.encode(rModel.getFirstname() + year + month + day));
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
            upe.setPositionId(14L);
            upe.setDepartmentId(4L);
            upe.setRoleId(3L);

            UserProfileEntity savedUpe = upRepo.save(upe);
            bae.setPreferred_dt(rModel.getPreferred_dt());
            bae.setCreatedDate(LocalDate.now());
            bae.setProfileId(savedUpe.getId());
            bae.setStatusId(9L);
            bae.setTestimony(rModel.getTestimony());
            bae.setCertificateStatus(1L);
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
        try {
            ApiResponseModel res = new ApiResponseModel();
            String queryFormatted = "%" + query + "%";
            int numberOfRowsToSkip = page == 1 ? 0 : (page - 1) * 10;
            List<ModifiedBaptismEntity> list = bRepo.paginatedBaptism(queryFormatted, numberOfRowsToSkip);
            List<BaptismResponseModel> data = list.stream().map(m -> {
                Optional<UserProfileEntity> uaeOptional = upRepo.findById(m.getProfileId());
                UserProfileEntity uae = uaeOptional.orElse(new UserProfileEntity());
                BaptismResponseModel br = new BaptismResponseModel();
                Optional<RoleEntity> reo = rRepo.findById(uae.getRoleId());
                RoleEntity roleEntity = reo.orElse(null);
                Optional<StatusEntity> sto = stRepo.findById(m.getStatusId());
                StatusEntity ste = sto.orElse(null);
                Optional<CertificateStatusEntity> cto = ctRepo.findById(m.getCertificateStatus());
                CertificateStatusEntity cte = cto.orElse(null);
                UserInfoModel infoModel = new UserInfoModel(
                        uae.getFirstname(),
                        uae.getMiddlename(),
                        uae.getLastname(),
                        uae.getAge(),
                        uae.getBirthdate(),
                        uae.getAddress(),
                        uae.getEmail(),
                        uae.getCreatedDate(),
                        uae.getContactNo(),
                        uae.getEmergencyContactPerson(),
                        uae.getEmergencyContactNo(),
                        uae.getGender(),
                        uae.getImageUUID(),
                        roleEntity.getRoleName()
                );
                br.setId(m.getId());
                br.setUserInfoModel(infoModel);
                br.setBaptismDate(m.getBaptismDate());
                br.setBaptismOfficiant(m.getBaptismOfficiant());
                br.setBaptismOfficiantId(m.getBaptismOfficiantId());
                br.setPreferred_dt(m.getPreferred_dt());
                br.setCreatedDate(m.getCreatedDate());
                br.setTestimony(m.getTestimony());
                br.setScheduledDate(m.getScheduledDate());
                br.setStatus(ste.getStatusName());
                br.setLocation(m.getLocation());
                br.setCertificateStatus(cte.getStatusName());
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

    @Override
    public ApiResponseModel getBaptismOfficiants() {
        ApiResponseModel res = new ApiResponseModel();
        try {
            List<UserProfileEntity> list = upRepo.findByRoleId(7L);
            List<UserAndIdModel> officiantList = list.stream().map(m -> {
                UserAndIdModel model = new UserAndIdModel();
                model.setId(m.getId());
                model.setFullName(m.getFirstname() + " " + m.getMiddlename() + " " + m.getLastname());
                return model;
            }).toList();
            res.setData(officiantList);
            res.setMessage(SUCCESS);
            res.setStatusCode(200);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel getCertificateStatuses() {
        ApiResponseModel res = new ApiResponseModel();
        try {
            List<CertificateStatusEntity> list = ctRepo.findAll();
            res.setData(list);
            res.setMessage(SUCCESS);
            res.setStatusCode(200);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel getBaptismStatuses() {
        ApiResponseModel res = new ApiResponseModel();
        try {
            List<StatusEntity> list = stRepo.findAll();
            res.setData(list);
            res.setMessage(SUCCESS);
            res.setStatusCode(200);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public ApiResponseModel sentBaptismSchedule(BaptismScheduleModel model) {
        try {
            ApiResponseModel res = new ApiResponseModel();
            Optional<BaptismEntity> beo = bRepo.findById(model.getId());
            BaptismEntity bae = beo.orElse(null);
            Optional<UserProfileEntity> upo = upRepo.findById(bae.getProfileId());
            UserProfileEntity upe = upo.orElse(null);
            Optional<UserAccountEntity> uao = userRepo.findById(upe.getUserId());
            UserAccountEntity uae = uao.orElse(null);
            MemberEntity me;
            LocalDateTime date = LocalDateTime.now();
            int year = date.getYear();
            int month = date.getMonthValue();
            int day = date.getDayOfMonth();
            String password = upe.getFirstname().replace(" ", "").toLowerCase() +year+month+day;
            uae.setPassword(encoder.encode(password));
            userRepo.save(uae);
            if (model.getBaptismStatusId() == 8) {
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
                UserProfileEntity savedUPE = upRepo.save(upe);
                me = mRepo.findByProfileId(savedUPE.getId());
                if(me != null) {
                    throw new RuntimeException("This account is already approved.");
                }
                me = new MemberEntity();
                bae.setStatusId(model.getBaptismStatusId());
                bae.setCertificateStatus(model.getCertificationId());
                bae.setBaptismDate(LocalDateTime.parse(model.getBaptismDate()));
                bae.setBaptismOfficiant(model.getBaptismOfficiant());
                bae.setBaptismOfficiantId(model.getBaptismOfficiantId());
                bae.setLocation(model.getLocation());
                bRepo.save(bae);
                me.setProfileId(upe.getId());
                me.setStatusId(8L);
                me.setActive(true);
                me.setJoinDate(date.toString());
                me.setMemberName(upe.getFirstname()+" "+upe.getMiddlename()+" "+upe.getLastname());
                mRepo.save(me);
                res.setStatusCode(200);
                res.setMessage(SUCCESS);
                return res;

            } else if(model.getBaptismStatusId() == 1) {
                upe.setRoleId(1L);
                upe.setPositionId(14L);
                upe.setDepartmentId(4L);
                upRepo.save(upe);
                String message = "Dear " + upe.getFirstname() + " " + upe.getLastname() + ",\n" +
                        "\n" +
                        "Thank you for showing interest in our church and taking this important step in your faith journey.\n" +
                        "\n" +
                        "We’re pleased to inform you that your baptism has been scheduled for " + model.getDateString() + ".\n" +
                        "\n" +
                        "Please arrive at least 30 minutes before the ceremony for registration and orientation. Our team will be there to guide and assist you throughout the process.\n" +
                        "\n" +
                        "If you have any questions or need to make changes to your schedule, feel free to reply to this email or contact us at (046) 123-4567 /  info@bbekawit.org .\n" +
                        "\n" +
                        "We look forward to celebrating this special moment with you!\n" +
                        "\n" +
                        "Blessings,\n" +
                        "BIBLE BAPTIST OF EKLESSIA\n" +
                        "485 Acacia St. Villa Ramirez Tabon 1, Kawit Cavite";

                emailSenderServiceImp.sendEmailMessage(model.getEmail(), message, "BAPTISM APPLICATION");

                bae.setStatusId(model.getBaptismStatusId());
                bae.setCertificateStatus(model.getCertificationId());
                bae.setBaptismDate(LocalDateTime.parse(model.getBaptismDate()));
                bae.setBaptismOfficiant(model.getBaptismOfficiant());
                bae.setBaptismOfficiantId(model.getBaptismOfficiantId());
                bae.setLocation(model.getLocation());
                bRepo.save(bae);
                res.setStatusCode(200);
                res.setMessage(SUCCESS);
                return res;
            }else{
                String message = "Dear " + upe.getFirstname() + " " + upe.getLastname() + ",\n" +
                        "Thank you for your interest in becoming a member of Bible Baptist of Eklessia. We truly appreciate the time, prayer, and thought you’ve given in taking this step toward joining our church community.\n" +
                        "\n" +
                        "After careful consideration and prayerful review, we regret to inform you that your membership application has not been approved at this time. Please know that this decision was made with much thought and discernment, and it does not reflect a lack of value or love for you as a person.\n" +
                        "\n" +
                        "We encourage you to continue attending our worship services and church activities. You are always welcome in our community, and we hope to continue walking with you in faith and fellowship.\n" +
                        "\n" +
                        "If you would like to discuss this further or seek guidance on how to move forward, please don’t hesitate to reach out to us. Our pastoral team would be happy to speak and pray with you.\n" +
                        "\n" +
                        "May God’s grace and peace continue to be with you always.\n" +
                        "\n" +
                        "With compassion and blessings," +
                        "\n" +
                        "Blessings,\n" +
                        "BIBLE BAPTIST OF EKLESSIA\n" +
                        "485 Acacia St. Villa Ramirez Tabon 1, Kawit Cavite";

                emailSenderServiceImp.sendEmailMessage(model.getEmail(), message, "BAPTISM APPLICATION");
                res.setStatusCode(200);
                res.setMessage(SUCCESS);
                return res;
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public ApiResponseModel addBaptism(AddBaptismRequestModel model) {
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
            //saving of account credentials
            uae.setUsername(model.getEmail());
            uae.setPassword(encoder.encode(model.getFirstname()+year+month+day));
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
            upe.setPositionId(14L);
            upe.setDepartmentId(4L);
            upe.setUserId(savedUAE.getId());
            UserProfileEntity savedUPE = upRepo.save(upe);
            //creating baptism schedule
            be.setBaptismDate(model.getBaptismDate());
            be.setBaptismOfficiant(model.getBaptismOfficiant());
            be.setBaptismOfficiantId(model.getBaptismOfficiantId());
            be.setLocation(model.getLocation());
            be.setProfileId(savedUPE.getId());
            be.setCertificateStatus(1L);
            be.setStatusId(1L);
            be.setLocation(model.getLocation());


            bRepo.save(be);

            String message = "Dear " + upe.getFirstname() + " " + upe.getLastname() + ",\n" +
                    "\n" +
                    "Thank you for showing interest in our church and taking this important step in your faith journey.\n" +
                    "\n" +
                    "We’re pleased to inform you that your baptism has been scheduled for " + model.getBaptismStringDate() + ".\n" +
                    "\n" +
                    "Please arrive at least 30 minutes before the ceremony for registration and orientation. Our team will be there to guide and assist you throughout the process.\n" +
                    "\n" +
                    "If you have any questions or need to make changes to your schedule, feel free to reply to this email or contact us at (046) 123-4567 /  info@bbekawit.org .\n" +
                    "\n" +
                    "We look forward to celebrating this special moment with you!\n" +
                    "\n" +
                    "Blessings,\n" +
                    "BIBLE BAPTIST OF EKLESSIA\n" +
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
