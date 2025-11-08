package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.EventEntity;
import com.bbek.BbekServiceA.entities.MinistryEntity;
import com.bbek.BbekServiceA.entities.MinistryStatusRfEntity;
import com.bbek.BbekServiceA.entities.modified.minsitry.ModifiedMinistryEntity;
import com.bbek.BbekServiceA.entities.pivot.EventPivotEntity;
import com.bbek.BbekServiceA.entities.pivot.MinistryPivotEntity;
import com.bbek.BbekServiceA.entities.reference.DepartmentEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.MinistryModel;
import com.bbek.BbekServiceA.repository.MinistryRepo;
import com.bbek.BbekServiceA.repository.MinistryStatusRepo;
import com.bbek.BbekServiceA.repository.pivot.MinistryPivotRepo;
import com.bbek.BbekServiceA.repository.reference.DepartmentRepo;
import com.bbek.BbekServiceA.service.MinistryService;
import com.bbek.BbekServiceA.util.Config;
import com.bbek.BbekServiceA.util.Dates;
import com.bbek.BbekServiceA.util.SaveFile;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bbek.BbekServiceA.util.Constant.FAILED;
import static com.bbek.BbekServiceA.util.Constant.SUCCESS;

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
    public ApiResponseModel saveMinistry(MinistryEntity entity, boolean isUpdate, String statusName, String department, MultipartFile file) {
        try {
            MinistryStatusRfEntity statusRfEntity = msRepo.findByStatusName(statusName);

            String ministryPath = Config.getMinistryImagePath();
            String fileUploadPathImage = ministryPath + "\\" + ((DateTimeFormatter.ofPattern("yyyy-MM")).format(LocalDateTime.now()));
            MinistryEntity entity1 = entity;
            DepartmentEntity de = dRepo.findByDepartmentName(department);
            entity1.setDepartmentId(de.getId());
            String filePath = "";
            if (isUpdate) {
                Optional<MinistryEntity> entityOptional = mRepo.findById(entity.getId());
                MinistryEntity oldEntity = entityOptional.orElse(null);
                entity1.setFilepath(oldEntity.getFilepath());
                entity1.setUpdatedDate(LocalDateTime.now());
            }else{
                entity1.setCreatedDate(LocalDateTime.now());
            }
            entity1.setStatusId(statusRfEntity.getId());

            if(file != null){
                File ROOT_BASE_PATH = new File(fileUploadPathImage);
                if (!ROOT_BASE_PATH.exists()) {
                    ROOT_BASE_PATH.mkdirs();
                }
                String[] ext = file.getOriginalFilename().split("\\.");
                filePath = fileUploadPathImage + "\\" + new Dates().getCurrentDateTime1() + "-" + new SaveFile().generateRandomString() + "." + ext[ext.length - 1];
                if (isUpdate) {
                    new SaveFile().deleteFile(entity1.getFilepath());
                }
                entity1.setFilepath(filePath);
                new SaveFile().saveFile(file, filePath);
            }
            mRepo.save(entity1);

            res.setMessage(isUpdate ? "Ministry is updated successfully" : "Ministry is created successfully");
            res.setStatusCode(isUpdate ? 200 : 201);
            return res;

        } catch (Exception e) {
            mRepo.save(entity);
            res.setMessage(FAILED);
            res.setStatusCode(401);
            e.printStackTrace();
            return res;

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
                Optional<MinistryStatusRfEntity> msEntityOptional = msRepo.findById(m.getStatusId());
                Optional<DepartmentEntity> deo = dRepo.findById(m.getDepartmentId());
                DepartmentEntity de = deo.orElse(null);

                MinistryStatusRfEntity ministryStatusRfEntity = msEntityOptional.orElse(null);

                return new MinistryModel(
                        m.getId(),
                        m.getSchedule(),
                        m.getLeader(),
                        ministryStatusRfEntity.getStatusName(),
                        m.getMinistryName(),
                        m.getDescription(),
                        de.getDepartmentName(),
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
            MinistryPivotEntity me = mpRepo.save(entity);
            return new ApiResponseModel("You successfully joined in this ministry.", 200, me);
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
}
