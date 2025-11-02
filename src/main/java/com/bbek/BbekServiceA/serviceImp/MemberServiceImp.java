package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.*;
import com.bbek.BbekServiceA.entities.modified.member.ModifiedMemberEntity;
import com.bbek.BbekServiceA.entities.modified.user_profile.ModifiedUserProfile;
import com.bbek.BbekServiceA.entities.reference.DepartmentEntity;
import com.bbek.BbekServiceA.entities.reference.PositionEntity;
import com.bbek.BbekServiceA.entities.reference.StatusEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.baptism.AddBaptismRequestModel;
import com.bbek.BbekServiceA.model.member.MemberResponseModel;
import com.bbek.BbekServiceA.repository.BaptismRepo;
import com.bbek.BbekServiceA.repository.MemberRepo;
import com.bbek.BbekServiceA.repository.UserProfileRepo;
import com.bbek.BbekServiceA.repository.UserRepo;
import com.bbek.BbekServiceA.repository.reference.*;
import com.bbek.BbekServiceA.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.bbek.BbekServiceA.util.Constant.SUCCESS;
import static com.bbek.BbekServiceA.util.Constant.USER_NOT_FOUND;

@Slf4j
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
    @Autowired
    PositionRepo positionRepo;
    @Autowired
    DepartmentRepo departmentRepo;

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
        ApiResponseModel res = new ApiResponseModel();
        try {
            UserAccountEntity uae = new UserAccountEntity();
            UserProfileEntity upe = new UserProfileEntity();
            BaptismEntity be = new BaptismEntity();
            MemberEntity me = new MemberEntity();

            UserAccountEntity checkEntity = userRepo.findByUsername(model.getEmail());
            if (checkEntity != null) return new ApiResponseModel("Email is already used.", 400, "");
            LocalDateTime dateTime = LocalDateTime.now();
            int year = dateTime.getYear();
            int month = dateTime.getMonthValue();
            int day = dateTime.getDayOfMonth();
            String password = model.getFirstname() + year + month + day;
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
            be.setCertificateStatus(2L);
            be.setCreatedDate(LocalDate.now());
            be.setStatusId(8L);
            be.setLocation(model.getLocation());
            bRepo.save(be);

            //add to member table
            me.setMemberName(savedUPE.getFirstname() + " " + savedUPE.getMiddlename() + " " + savedUPE.getLastname());
            me.setActive(true);
            me.setStatusId(8L);
            me.setJoinDate(year + "-" + month + "-" + day);
            me.setProfileId(savedUPE.getId());
            memberRepo.save(me);

            String message = "Dear " + upe.getFirstname() + " " + upe.getLastname() + " ,\n" +
                    "\n" +
                    "We are overjoyed to welcome you as an official member of Bible Baptist of Eklessia! Your decision to become part of our community is truly a blessing, and we thank God for guiding you to our family of faith.\n" +
                    "\n" +
                    "As a member, you are now part of a loving and supportive community that seeks to grow together in faith, serve others with compassion, and share the message of God’s love. We encourage you to join our worship services, ministry activities, and fellowship gatherings, where you can deepen your connection with God and with your fellow believers.\n" +
                    "We also generate an account for you so that you can login in our website and view upcoming events or become part of our ministries activities. \n" +
                    "Username: " + uae.getUsername() + "\n" +
                    "Password: " + password + "\n" +
                    "If you have any questions or would like to get involved in our ministries, please don’t hesitate to reach out to us. We look forward to walking alongside you in your spiritual journey.\n" +
                    "\n" +
                    "Once again, welcome to the family — we’re so glad you’re here!\n" +
                    "\n" +
                    "With blessings and joy,\n" +
                    "Staff Admin\n" +
                    "BIBLE BAPTIST OF EKLESSIA\n" +
                    "(046) 123-4567 /  info@bbekawit.org\n" +
                    "485 Acacia St. Villa Ramirez Tabon 1, Kawit Cavite";

            emailSenderServiceImp.sendEmailMessage(model.getEmail(), message, "BAPTISM APPLICATION");
            res.setStatusCode(200);
            res.setMessage(SUCCESS);
            return res;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel viewDetails(Long memberId) {
        ApiResponseModel res = new ApiResponseModel();
        try {
            MemberDetailsEntity entity = memberRepo.viewDetails(memberId);
            if (entity == null) {
                return new ApiResponseModel("Details not found", 404, "");
            }
            res.setStatusCode(200);
            res.setData(entity);
            res.setMessage(SUCCESS);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel getDepartmentList() {
        ApiResponseModel res = new ApiResponseModel();
        try {
            List<DepartmentEntity> list = departmentRepo.findAll();
            res.setData(list);
            res.setStatusCode(200);
            res.setMessage(SUCCESS);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel getPositionList() {
        ApiResponseModel res = new ApiResponseModel();
        try {
            List<PositionEntity> list = positionRepo.findAll();
            res.setData(list);
            res.setStatusCode(200);
            res.setMessage(SUCCESS);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel editMemberDetails(MemberDetailsEntity entity) {
        ApiResponseModel res = new ApiResponseModel();
        try {
            UserAccountEntity uae = new UserAccountEntity();
            UserProfileEntity upe = new UserProfileEntity();
            BaptismEntity be = new BaptismEntity();
            MemberEntity me = new MemberEntity();

            // saving profile
            Optional<UserProfileEntity> upo = upRepo.findById(entity.getId());
            upe = upo.orElse(null);
            if (upe == null) return new ApiResponseModel(USER_NOT_FOUND, 404, "");
            upe.setFirstname(entity.getFirstname());
            upe.setMiddlename(entity.getMiddlename());
            upe.setLastname(entity.getLastname());
            upe.setAge(entity.getAge());
            upe.setBirthdate(entity.getBirthdate());
            upe.setAddress(entity.getAddress());
            upe.setGender(entity.getGender());
            upe.setEmail(entity.getEmail());
            upe.setContactNo(entity.getContactNo());
            if (entity.getPositionId() == 12L) {
                upe.setRoleId(6L);
            } else if (entity.getPositionId() == 10 || entity.getPositionId() == 11) {
                upe.setRoleId(7L);
            } else if (entity.getPositionId() == 13) {
                upe.setRoleId(2L);
            } else {
                upe.setRoleId(1L);
            }
            upe.setEmergencyContactNo(entity.getEmergencyContactNo());
            upe.setEmergencyContactPerson(entity.getEmergencyContactPerson());
            upe.setRelationshipToContactPerson(entity.getRelationshipToContactPerson());
            upe.setDepartmentId(entity.getDepartmentId());
            upe.setPositionId(entity.getPositionId());
            UserProfileEntity savedUAE = upRepo.save(upe);

            //saving account
            Optional<UserAccountEntity> uao = userRepo.findById(savedUAE.getUserId());
            uae = uao.orElse(null);
            if (uae == null) return new ApiResponseModel(USER_NOT_FOUND, 404, "");
            uae.setUsername(entity.getEmail());
            userRepo.save(uae);

            //saving baptism
            be = bRepo.findByProfileId(savedUAE.getId());
            be.setBaptismOfficiant(entity.getBaptismOfficiant());
            be.setBaptismOfficiantId(entity.getBaptismOfficiantId());
            be.setBaptismDate(entity.getBaptismDate());
            bRepo.save(be);

            //saving member flags
            me = memberRepo.findByProfileId(savedUAE.getId());
            me.setJoinDate(String.valueOf(entity.getJoinDate()));
            me.setActive(entity.isActive());
            me.setMemberName(savedUAE.getFirstname() + " " + savedUAE.getMiddlename() + " " + savedUAE.getLastname());
            memberRepo.save(me);

            res.setMessage("Member details updated successfully.");
            res.setStatusCode(200);
            return res;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ApiResponseModel fetchDepartmentsMembers(String query, int page) {
        ApiResponseModel res = new ApiResponseModel();

        try {
            String queryFormatted = "%" + query + "%";
            int numberOfRowsToSkip = page == 1 ? 0 : (page - 1) * 10;
            List<ModifiedUserProfile>  list = upRepo.findDepartmentsMembers(queryFormatted, numberOfRowsToSkip);
            if(list == null) return new ApiResponseModel("Failed to fetch list of members. Please try again.", 500, null);
            res.setStatusCode(200);
            res.setData(list);
            res.setMessage(SUCCESS);
            return res;

        } catch (Exception e) {
            log.error("Message", e);
            return new ApiResponseModel("Failed to fetch list of members. Please try again.", 500, null);
        }
    }

    @Override
    public ApiResponseModel fetchPriestMembers(String query, int page) {

        ApiResponseModel res = new ApiResponseModel();

        try {
            String queryFormatted = "%" + query + "%";
            int numberOfRowsToSkip = page == 1 ? 0 : (page - 1) * 10;
            List<ModifiedUserProfile>  list = upRepo.findPriestMembers(queryFormatted, numberOfRowsToSkip);
            if(list == null) return new ApiResponseModel("Failed to fetch list of members. Please try again.", 500, null);
            res.setStatusCode(200);
            res.setData(list);
            res.setMessage(SUCCESS);
            return res;

        } catch (Exception e) {
            log.error("Message", e);
            return new ApiResponseModel("Failed to fetch list of members. Please try again.", 500, null);
        }

    }


}
