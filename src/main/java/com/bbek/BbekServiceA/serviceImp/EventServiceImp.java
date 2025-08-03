package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.EventEntity;
import com.bbek.BbekServiceA.entities.EventStatusRfEntity;
import com.bbek.BbekServiceA.entities.MinistryEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.EventModel;
import com.bbek.BbekServiceA.model.MinistryModel;
import com.bbek.BbekServiceA.repository.EventRepo;
import com.bbek.BbekServiceA.repository.EventStatusRepo;
import com.bbek.BbekServiceA.service.EventService;
import com.bbek.BbekServiceA.util.Config;
import com.bbek.BbekServiceA.util.Dates;
import com.bbek.BbekServiceA.util.SaveFile;
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
public class EventServiceImp implements EventService {
    @Autowired
    EventRepo eRepo;
    @Autowired
    EventStatusRepo esRepo;

    @Override
    public List<EventModel> getAllevent() {
        List<EventEntity> eventEntities = eRepo.findAll();
        return eventEntities.stream().map(m->{
            Optional<EventStatusRfEntity> statusOptional = esRepo.findById(m.getId());
            String statusName = statusOptional.map(EventStatusRfEntity::getStatusName).orElse("Unknown");
            return new EventModel(
                    m.getId(),
                    m.getEventName(),
                    m.getEventType(),
                    m.getEventDate(),
                    m.getEvent_time(),
                    m.getEventLocation(),
                    m.getAttendance(),
                    m.getOffering(),
                    statusName,
                    m.getDescription()
            );
        }).collect(Collectors.toList());
    }

    @Override
    public ApiResponseModel saveEvent(   EventEntity entity,
                                         MultipartFile file,
                                         boolean isUpdate,
                                         String statusName) {
        ApiResponseModel res = new ApiResponseModel();
        try{
            EventStatusRfEntity eventStatusRfEntity= esRepo.findByStatusName(statusName);
            if(eventStatusRfEntity == null){
                res.setMessage("Status not found");
                res.setStatusCode(404);
                return res;
            }
            String eventPath = Config.getEventImagePath();
            String fileUploadPathImage = eventPath + "\\" + ((DateTimeFormatter.ofPattern("yyyy-MM")).format(LocalDateTime.now()));
            File ROOT_BASE_PATH = new File(fileUploadPathImage);
            if(!ROOT_BASE_PATH.exists()){
                ROOT_BASE_PATH.mkdirs();
            }
            String[] ext = file.getOriginalFilename().split("\\.");
            String filePath = fileUploadPathImage+"\\"+new Dates().getCurrentDateTime1()+"-"+new SaveFile().generateRandomString()+"."+ext[ext.length-1];
            EventEntity entity1 = entity;
            entity1.setFilePath(filePath);
            entity1.setStatusId(eventStatusRfEntity.getId());
            eRepo.save(entity);
            new SaveFile().saveFile(file, filePath);

            res.setMessage(isUpdate?"Event successfully updated.":"Event successfully created.");
            res.setStatusCode(isUpdate?200:201);
            return res;

        } catch (Exception e) {
            eRepo.save(entity);
            res.setMessage(FAILED);
            res.setStatusCode(401);
            e.printStackTrace();
            return res;

        }
    }

    @Override
    public ApiResponseModel getEvent(Long id) {
        ApiResponseModel res = new ApiResponseModel();
        try{
          Optional<EventEntity> eEntityOPtional = eRepo.findById(id);
          EventEntity eventEntity = eEntityOPtional.orElse(null);
          if(eventEntity == null){
              res.setMessage("Not found");
              res.setStatusCode(404);
              return res;
          }
          Optional<EventStatusRfEntity> esRfEntityOptional = esRepo.findById(eventEntity.getStatusId());
          EventStatusRfEntity eventStatusRfEntity = esRfEntityOptional.orElse(null);
          if(eventStatusRfEntity == null){
              res.setMessage("Status Not found");
              res.setStatusCode(404);
              return res;
          }
          EventModel eventModel = new EventModel(
             eventEntity.getId(),
             eventEntity.getEventName(),
             eventEntity.getEventType(),
             eventEntity.getEventDate(),
             eventEntity.getEvent_time(),
             eventEntity.getEventLocation(),
             eventEntity.getAttendance(),
             eventEntity.getOffering(),
             eventStatusRfEntity.getStatusName(),
             eventEntity.getDescription()
          );
          res.setStatusCode(200);
          res.setMessage(SUCCESS);
          res.setData(eventModel);
          return res;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ApiResponseModel updateEvent(EventEntity entity, MultipartFile file) {
        ApiResponseModel res = new ApiResponseModel();
        try{
            String eventPath = Config.getEventImagePath();
            String fileUploadPathImage = eventPath + "\\" + ((DateTimeFormatter.ofPattern("yyyy-MM")).format(LocalDateTime.now()));
            File ROOT_BASE_PATH = new File(fileUploadPathImage);
            if(!ROOT_BASE_PATH.exists()){
                ROOT_BASE_PATH.mkdirs();
            }
            String[] ext = file.getOriginalFilename().split("\\.");
            String filePath = fileUploadPathImage+"\\"+new Dates().getCurrentDateTime1()+"-"+new SaveFile().generateRandomString()+"."+ext[ext.length-1];
            EventEntity entity1 = entity;
            entity1.setFilePath(filePath);
            eRepo.save(entity);
            new SaveFile().saveFile(file, filePath);
            res.setMessage("Event successfully updated.");
            res.setStatusCode(200);
            return res;

        } catch (Exception e) {
            eRepo.save(entity);
            res.setMessage(FAILED);
            res.setStatusCode(401);
            e.printStackTrace();
            return res;

        }
    }

    @Override
    public ApiResponseModel deleteEvent(Long id) {
        ApiResponseModel res = new ApiResponseModel();
        try{
            Optional<EventEntity> eventEntityOptional = eRepo.findById(id);
            EventEntity eventEntity = eventEntityOptional.orElse(null);
            if(eventEntity == null){
                res.setMessage("Not found");
                res.setStatusCode(404);
                return res;
            }
            eRepo.delete(eventEntity);
            res.setMessage("Event delete successfully.");
            res.setStatusCode(200);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getEventImage(String eventName) {
        System.out.println(eventName);
        return eRepo.findFilePathByName(eventName);
    }
}
