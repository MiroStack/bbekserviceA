package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.MemberEntity;
import com.bbek.BbekServiceA.entities.UserProfileEntity;
import com.bbek.BbekServiceA.entities.history.HistoryLogEntity;
import com.bbek.BbekServiceA.entities.ministries.MinistryEntity;
import com.bbek.BbekServiceA.entities.ministries.MinistryMemberEntity;
import com.bbek.BbekServiceA.entities.ministries.MinistryStatusRfEntity;
import com.bbek.BbekServiceA.entities.modified.minsitry.ModifiedMinistryEntity;
import com.bbek.BbekServiceA.entities.pivot.MinistryPivotEntity;
import com.bbek.BbekServiceA.entities.reference.ApplicationStatusEntity;
import com.bbek.BbekServiceA.entities.reference.DepartmentEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.ministry.CreateMinistryModel;
import com.bbek.BbekServiceA.model.ministry.MinistryModel;
import com.bbek.BbekServiceA.model.ministry.TotalMinistryModel;
import com.bbek.BbekServiceA.repository.MemberRepo;
import com.bbek.BbekServiceA.repository.MinistryRepo;
import com.bbek.BbekServiceA.repository.MinistryStatusRepo;
import com.bbek.BbekServiceA.repository.history.HistoryLogRepo;
import com.bbek.BbekServiceA.repository.pivot.MinistryPivotRepo;
import com.bbek.BbekServiceA.repository.reference.ApplicationStatusRepo;
import com.bbek.BbekServiceA.repository.reference.DepartmentRepo;
import com.bbek.BbekServiceA.service.MinistryService;
import com.bbek.BbekServiceA.util.Config;
import com.bbek.BbekServiceA.util.Dates;
import com.bbek.BbekServiceA.util.ExceptionLogger;
import com.bbek.BbekServiceA.util.SaveFile;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bbek.BbekServiceA.util.Config.getMinistryImagePath;
import static com.bbek.BbekServiceA.util.Constant.*;
import static java.lang.Integer.parseInt;

@Service
public class MinistryServiceImp implements MinistryService {
    @Autowired
    MinistryRepo mRepo;

    @Autowired
    MinistryStatusRepo msRepo;

    @Autowired
    DepartmentRepo dRepo;

    @Autowired
    MinistryPivotRepo mpRepo;

    @Autowired
    ApplicationStatusRepo asRepo;

    @Autowired
    HistoryLogRepo hlRepo;

    @Autowired
    MemberRepo memberRepo;

    @Autowired
    ExceptionLogger exceptionLogger;

    private final ApiResponseModel res = new ApiResponseModel();

    @Override
    public List<MinistryModel> getAllMinistryList(String query, int page) {
        System.out.println("Page: "+ page);

        int numberOfRowsToSkip = page == 1? 0 : (page - 1) * 10;
        List<ModifiedMinistryEntity> ministryEntities = mRepo.getPaginatedMinistry(query, numberOfRowsToSkip);
        return ministryEntities.stream().map(m -> {
            Optional<MinistryStatusRfEntity> msEntityOptional = msRepo.findById(m.getStatusId());
            MinistryStatusRfEntity ministryStatusRfEntity = msEntityOptional.orElse(null);
            return new MinistryModel(
                    m.getId(),
                    m.getSchedule(),
                    m.getLeader(),
                    ministryStatusRfEntity.getStatusName(),
                    m.getMinistryName(),
                    m.getDescription(),
                    m.getDepartmentName(),
                    m.getMember(),
                    m.getCreatedDate(),
                    m.getUpdatedDate(),
                    m.getStartTime(),
                    m.getEndTime(),
                    m.getTotalRows()
            );
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ApiResponseModel saveMinistry(CreateMinistryModel model) {
        try {
            MinistryStatusRfEntity statusRfEntity = msRepo.findByStatusName(model.getStatus());
            if(statusRfEntity == null) return new ApiResponseModel("Invalid status!", 400, null);

            DepartmentEntity departmentEntity = dRepo.findByDepartmentName(model.getDepartment());
            if(departmentEntity == null) return new ApiResponseModel("Invalid department!", 400, null);
           if(model.isUpdate()){
               Optional<MinistryEntity> meo = mRepo.findById(model.getId());
               MinistryEntity me=meo.orElse(null);
               if(me == null)return new ApiResponseModel("Ministry not found",404,null);
               me.setMinistryName(model.getMinistryName());
               me.setSchedule(model.getScheduleDay());
               me.setLeader(model.getMinistryLeader());
               me.setStatusId(statusRfEntity.getId());
               me.setDescription(model.getDescription());
               if(model.getMinistryImage() != null){
                   String root = getMinistryImagePath();
                   String filepath = new SaveFile().savedImage(root,model.getMinistryImage());
                   me.setFilepath(filepath);
               }
               me.setCreatedDate(LocalDateTime.now());
               me.setStartTime(LocalTime.parse(model.getStartTime()));
               me.setEndTime(LocalTime.parse(model.getEndTime()));
               me.setDepartmentId(departmentEntity.getId());
               mRepo.save(me);
           }else{
               MinistryEntity entity = new MinistryEntity();
               entity.setMinistryName(model.getMinistryName());
               entity.setSchedule(model.getScheduleDay());
               entity.setLeader(model.getMinistryLeader());
               entity.setStatusId(statusRfEntity.getId());
               entity.setDescription(model.getDescription());
               entity.setMember(0);
               String root = getMinistryImagePath();
               String filepath = new SaveFile().savedImage(root,model.getMinistryImage());
               entity.setFilepath(filepath);
               entity.setCreatedDate(LocalDateTime.now());
               entity.setStartTime(LocalTime.parse(model.getStartTime()));
               entity.setEndTime(LocalTime.parse(model.getEndTime()));
               entity.setDepartmentId(departmentEntity.getId());
               mRepo.save(entity);
           }
            return new ApiResponseModel(model.isUpdate()?"Ministry updated successfully":"Ministry created successfully", model.isUpdate()?200:201, null);
        } catch (Exception e) {
            exceptionLogger.logError(getClass().getSimpleName(), e);
            return new ApiResponseModel("Something went wrong. Please try again.", 500, null);
        }

    }

    @Override
    public String getMinistryImage(String ministryName) {
        System.out.println(ministryName);
        return mRepo.findFilePathByName(ministryName);
    }

    @Override
    public ApiResponseModel getMinistry(Long id) {
        try {
            Optional<MinistryEntity> ministryEntityOptional = mRepo.findById(id);
            MinistryEntity ministryEntity = ministryEntityOptional.orElse(null);
            if (ministryEntity == null) {
                res.setStatusCode(404);
                res.setMessage("Ministry not found");
                return res;
            }
            Optional<MinistryStatusRfEntity> msEntityOptional = msRepo.findById(ministryEntity.getStatusId());
            MinistryStatusRfEntity ministryStatusRfEntity = msEntityOptional.orElse(null);
            if ((ministryStatusRfEntity == null)) {
                res.setStatusCode(404);
                res.setMessage("Status not found");
                return res;
            }
            Optional<DepartmentEntity> deo = dRepo.findById(ministryEntity.getDepartmentId());
            DepartmentEntity de = deo.orElse(null);
            if ((de == null)) {
                res.setStatusCode(404);
                res.setMessage("Department not found");
                return res;
            }
            MinistryModel model = new MinistryModel(
                    ministryEntity.getId(),
                    ministryEntity.getSchedule(),
                    ministryEntity.getLeader(),
                    ministryStatusRfEntity.getStatusName(),
                    ministryEntity.getMinistryName(),
                    ministryEntity.getDescription(),
                    de.getDepartmentName(),
                    ministryEntity.getMember(),
                    ministryEntity.getCreatedDate(),
                    ministryEntity.getUpdatedDate(),
                    ministryEntity.getStartTime(),
                    ministryEntity.getEndTime(),
                    0
            );
            res.setData(model);
            res.setMessage(SUCCESS);
            res.setStatusCode(200);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel deleteMinistry(Long id) {
        try {
            Optional<MinistryEntity> ministryEntityOptional = mRepo.findById(id);
            MinistryEntity ministryEntity = ministryEntityOptional.orElse(null);
            if (ministryEntity == null) {
                res.setStatusCode(404);
                res.setMessage("Ministry not found");
                return res;
            }
            new SaveFile().deleteFile(ministryEntity.getFilepath());
            mRepo.delete(ministryEntity);
            res.setMessage("Ministry deleted successfully");
            res.setStatusCode(200);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public ApiResponseModel getUpcomingMinistry() {
        try {
            List<MinistryEntity> entities = mRepo.findUpcomingMinistry();
            List<MinistryModel> ministryModelList = entities.stream().map(m -> {

                return new MinistryModel(
                        m.getId(),
                        m.getSchedule(),
                        m.getLeader(),
                        "",
                        m.getMinistryName(),
                        m.getDescription(),
                        "",
                        m.getMember(),
                        m.getCreatedDate(),
                        m.getUpdatedDate(),
                        m.getStartTime(),
                        m.getEndTime(),
                        0
                );
            }).toList();
            res.setMessage(SUCCESS);
            res.setStatusCode(200);
            res.setData(ministryModelList);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ApiResponseModel getPaginatedMinistry(String query, int page) {
        try{

            int numberOfRowsToSkip = page == 1? 0 : (page - 1) * 10;
            List<ModifiedMinistryEntity> list = mRepo.getPaginatedMinistry(query, numberOfRowsToSkip);
            res.setData(list);
            res.setStatusCode(200);
            res.setMessage(SUCCESS);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel getMinistryStatuses() {
        try{
            List<MinistryStatusRfEntity> list = msRepo.findAll();
            res.setData(list);
            res.setMessage(SUCCESS);
            res.setStatusCode(200);
            return res;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel joinMinistry(MinistryPivotEntity entity) {
        try{
            MinistryEntity entity1 = new MinistryEntity();
             Optional<MinistryEntity> meo = mRepo.findById(entity.getMinistryId());
             entity1 = meo.orElse(null);
            if(entity1 == null) return new ApiResponseModel("Ministry not found.", 404, null);

            List<MinistryPivotEntity> ministryPivotEntityList = mpRepo.findByMemberId(entity.getMemberId());
            for(MinistryPivotEntity entity2:ministryPivotEntityList){
                if(entity2.getMinistryId() == entity.getMinistryId()){
                    return new ApiResponseModel("You already joined in this ministry", 400, null);
                }
            }
            MinistryPivotEntity mpe = new MinistryPivotEntity();
            mpe.setStatusId(1L);
            mpe.setMinistryId(entity.getMinistryId());
            mpe.setMemberId(entity.getMemberId());
            mpe.setCreatedDt(LocalDateTime.now());

            MinistryPivotEntity me = mpRepo.save(mpe);
            return new ApiResponseModel("Your application is successfully submitted.", 200, me);
        } catch (Exception e) {
            return new ApiResponseModel("Can't process your request. Please try again later", 500, null);
        }
    }

    @Override
    public ApiResponseModel leaveMinistry(Long id) {

        try {
            Optional<MinistryPivotEntity> mpo = mpRepo.findById(id);
            MinistryPivotEntity mp = mpo.orElse(null);
            if (mp == null) return new ApiResponseModel("Unauthorized user. Please re-login your account.", 401, null);
            mpRepo.delete(mp);
            return new ApiResponseModel("You successfully leave in this ministry.", 200, null);
        } catch (Exception e) {
            return new ApiResponseModel("Can't process your request. Please try again later", 500, null);
        }
    }

    @Override
    public List<MinistryModel> getAllLadiesMinistries(String query, int page) {
        int numberOfRowsToSkip = page == 1? 0 : (page - 1) * 10;
        List<ModifiedMinistryEntity> ministryEntities = mRepo.getAllWomenMinistry(query, numberOfRowsToSkip);
        List<MinistryModel> ministryModelList = ministryEntities.stream().map(m->{
            Optional<MinistryStatusRfEntity> msEntityOptional = msRepo.findById(m.getStatusId());
            MinistryStatusRfEntity ministryStatusRfEntity = msEntityOptional.orElse(null);
            return new MinistryModel(
                    m.getId(),
                    m.getSchedule(),
                    m.getLeader(),
                    ministryStatusRfEntity.getStatusName(),
                    m.getMinistryName(),
                    m.getDescription(),
                    m.getDepartmentName(),
                    m.getMember(),
                    m.getCreatedDate(),
                    m.getUpdatedDate(),
                    m.getStartTime(),
                    m.getEndTime(),
                    m.getTotalRows()
            );
        }).collect(Collectors.toList());
        return ministryModelList;
    }

    @Override
    public List<MinistryModel> getAllMenMinistries(String query, int page) {
        int numberOfRowsToSkip = page == 1? 0 : (page - 1) * 10;
        List<ModifiedMinistryEntity> ministryEntities = mRepo.getAllMenMinistry(query, numberOfRowsToSkip);
        List<MinistryModel> ministryModelList = ministryEntities.stream().map(m->{
            Optional<MinistryStatusRfEntity> msEntityOptional = msRepo.findById(m.getStatusId());
            MinistryStatusRfEntity ministryStatusRfEntity = msEntityOptional.orElse(null);
            return new MinistryModel(
                    m.getId(),
                    m.getSchedule(),
                    m.getLeader(),
                    ministryStatusRfEntity.getStatusName(),
                    m.getMinistryName(),
                    m.getDescription(),
                    m.getDepartmentName(),
                    m.getMember(),
                    m.getCreatedDate(),
                    m.getUpdatedDate(),
                    m.getStartTime(),
                    m.getEndTime(),
                    m.getTotalRows()
            );
        }).collect(Collectors.toList());
        return ministryModelList;
    }

    @Override
    public List<MinistryModel> getYoungPeopleMinistries(String query, int page) {
        int numberOfRowsToSkip = page == 1? 0 : (page - 1) * 10;
        List<ModifiedMinistryEntity> ministryEntities =  mRepo.getYoungPeopleMinistry(query, numberOfRowsToSkip);
        List<MinistryModel> ministryModelList = ministryEntities.stream().map(m->{
            Optional<MinistryStatusRfEntity> msEntityOptional = msRepo.findById(m.getStatusId());
            MinistryStatusRfEntity ministryStatusRfEntity = msEntityOptional.orElse(null);
            return new MinistryModel(
                    m.getId(),
                    m.getSchedule(),
                    m.getLeader(),
                    ministryStatusRfEntity.getStatusName(),
                    m.getMinistryName(),
                    m.getDescription(),
                    m.getDepartmentName(),
                    m.getMember(),
                    m.getCreatedDate(),
                    m.getUpdatedDate(),
                    m.getStartTime(),
                    m.getEndTime(),
                    m.getTotalRows()
            );
        }).collect(Collectors.toList());
        return ministryModelList;
    }

    @Override
    public List<MinistryModel> getMyMinistry(String query, int page, Long userId) {
        int numberOfRowsToSkip = page == 1? 0 : (page - 1) * 10;
        List<ModifiedMinistryEntity> ministryEntities =   mRepo.getMyMinistries(query, numberOfRowsToSkip, userId);
        List<MinistryModel> ministryModelList = ministryEntities.stream().map(m->{
            Optional<MinistryStatusRfEntity> msEntityOptional = msRepo.findById(m.getStatusId());
            MinistryStatusRfEntity ministryStatusRfEntity = msEntityOptional.orElse(null);
            return new MinistryModel(
                    m.getId(),
                    m.getSchedule(),
                    m.getLeader(),
                    ministryStatusRfEntity.getStatusName(),
                    m.getMinistryName(),
                    m.getDescription(),
                    m.getDepartmentName(),
                    m.getMember(),
                    m.getCreatedDate(),
                    m.getUpdatedDate(),
                    m.getStartTime(),
                    m.getEndTime(),
                    m.getTotalRows()
            );
        }).collect(Collectors.toList());
        return ministryModelList;
    }

    @Override
    public ApiResponseModel updateMemberJoinApplication(Long pivotId, String statusName, Long userId) {
       try{
           Optional<MinistryPivotEntity> mpo = mpRepo.findById(pivotId);
           MinistryPivotEntity mpe = mpo.orElse(null);
           if(mpe == null) return new ApiResponseModel(NOT_FOUND, 404, null);
           ApplicationStatusEntity ase = asRepo.findByStatusName(statusName);
           mpe.setStatusId(ase.getId());
           mpe.setModifiedBy(userId);
           mpe.setModifiedDt(LocalDateTime.now());

           MinistryPivotEntity savedEntity = mpRepo.save(mpe);

           Optional<MinistryEntity> ministryEntityOptional = mRepo.findById(mpe.getMinistryId());
           MinistryEntity ministryEntity = ministryEntityOptional.orElse(null);
           int totalMember = ministryEntity.getMember()+1;
           ministryEntity.setMember(totalMember);
           mRepo.save(ministryEntity);

           Optional<MemberEntity> meo = memberRepo.findById(userId);
           MemberEntity me = meo.orElse(null);


           Optional<MemberEntity> meo1 = memberRepo.findById(userId);
           MemberEntity me1 = meo1.orElse(null);
           if(me1 == null || me == null)return new ApiResponseModel(NOT_FOUND, 404, null);

           //create history
           HistoryLogEntity he = new HistoryLogEntity();
           he.setCategory("Ministry");
           he.setName(me1.getMemberName());
           he.setCreatedById(userId);
           he.setStatus(statusName);
           he.setParentId(savedEntity.getId());
           he.setCreatedDt(LocalDateTime.now());
           he.setDescription("Application of "+me.getMemberName()+"for minsitry "+ministryEntity.getMinistryName()+" is updated to "+statusName+" by "+me1.getMemberName());
           hlRepo.save(he);

           return new ApiResponseModel(SUCCESS, 200, null);
       } catch (Exception e) {
           return  new ApiResponseModel(FAILED, 500, null);
       }
    }

    @Override
    public ApiResponseModel viewMembersOfMinistries(Long ministryId, String query, int page) {
        try{
            int numberOfRowsToSkip = page == 1? 0 : (page - 1) * 10;
            List<MinistryMemberEntity> list = mRepo.findAllMinistryMembers(ministryId, query, numberOfRowsToSkip);
            return new ApiResponseModel(SUCCESS, 200, list);
        } catch (Exception e) {
            return  new ApiResponseModel(FAILED, 500, null);
        }
    }

    @Override
    public ApiResponseModel viewTotalMembersPerMinistry(Long ministryId) {
        try{
            Long totalMembers = mRepo.totalMembersPerMinistry(ministryId);
            return new ApiResponseModel(SUCCESS, 200, totalMembers);
        } catch (Exception e) {
            return  new ApiResponseModel(FAILED, 500, null);
        }
    }

    @Override
    public ApiResponseModel getTotalMinistryAndMembers() {
        try{
            Long totalMembers = mRepo.totalMinistryMembers();
            Long totalActiveMinistries = mRepo.totalActiveMinistries();
            Long totalMinistries = mRepo.totalMinistries();
            TotalMinistryModel model = new TotalMinistryModel(totalMinistries,totalActiveMinistries,totalMembers);
            return new ApiResponseModel(SUCCESS, 200, model);

        } catch (Exception e) {
            return  new ApiResponseModel(FAILED, 500, null);
        }

    }
}
