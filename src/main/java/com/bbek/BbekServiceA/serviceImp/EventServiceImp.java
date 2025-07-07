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
                    m.getEventName(),
                    m.getEventType(),
                    m.getEventDate(),
                    m.getEvent_time(),
                    m.getEventLocation(),
                    m.getAttendance(),
                    m.getOffering(),
                    statusName
            );
        }).collect(Collectors.toList());
    }

    @Override
    public ApiResponseModel saveEvent(   EventEntity entity,
                                         MultipartFile file ) {
        ApiResponseModel res = new ApiResponseModel();
        try{
            String ministryPath = Config.getEventImagePath();
            String fileUploadPathImage = ministryPath + "\\" + ((DateTimeFormatter.ofPattern("yyyy-MM")).format(LocalDateTime.now()));
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

            res.setMessage(SUCCESS);
            res.setStatusCode(201);
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
    public String getEventImage(String eventName) {
        System.out.println(eventName);
        return eRepo.findFilePathByName(eventName);
    }
}
