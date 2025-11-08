package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.EventEntity;
import com.bbek.BbekServiceA.entities.EventStatusRfEntity;
import com.bbek.BbekServiceA.entities.modified.event.ModifiedEventEntity;
import com.bbek.BbekServiceA.entities.pivot.EventPivotEntity;
import com.bbek.BbekServiceA.entities.pivot.MinistryPivotEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.event.EventModel;
import com.bbek.BbekServiceA.repository.EventRepo;
import com.bbek.BbekServiceA.repository.EventStatusRepo;
import com.bbek.BbekServiceA.repository.pivot.EventPivotRepo;
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

    @Autowired
    EventPivotRepo epRepo;

    private final ApiResponseModel res = new ApiResponseModel();

    @Override
    public List<EventModel> getAllevent(String query, int page, String status) {
        System.out.println("Page: " + page);
        String queryFormatted = "%" + query + "%";
        int numberOfRowsToSkip = page == 1 ? 0 : (page - 1) * 10;
        List<ModifiedEventEntity> eventEntities = eRepo.paginatedEvents(queryFormatted, numberOfRowsToSkip, status);

        try {
            return eventEntities.stream().map(m -> {
                Optional<EventStatusRfEntity> statusOptional = esRepo.findById(m.getStatusId());
                String statusName = statusOptional.map(EventStatusRfEntity::getStatusName).orElse("Unknown");
                return new EventModel(
                        m.getId(),
                        m.getEventName(),
                        m.getEventType(),
                        m.getEventStartDate(),
                        m.getEventEndDate(),
                        m.getEventLocation(),
                        m.getAttendance(),
                        m.getOffering(),
                        statusName,
                        m.getDescription(),
                        m.getTotalRows()
                );
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public ApiResponseModel saveEvent(EventEntity entity,
                                      MultipartFile file,
                                      boolean isUpdate,
                                      String statusName) {

        try {
            EventStatusRfEntity eventStatusRfEntity = esRepo.findByStatusName(statusName);
            //  System.out.println("Status name:" +statusName +", Status id:"+eventStatusRfEntity.getId());
            if (eventStatusRfEntity == null) {
                res.setMessage("Status not found");
                res.setStatusCode(404);
                return res;
            }
            String eventPath = Config.getEventImagePath();
            String fileUploadPathImage = eventPath + "\\" + ((DateTimeFormatter.ofPattern("yyyy-MM")).format(LocalDateTime.now()));
            EventEntity entity1 = entity;
            String filePath = "";
            if (isUpdate) {
                Optional<EventEntity> entityOptional = eRepo.findById(entity.getId());
                EventEntity oldEntity = entityOptional.orElse(null);

                entity1.setFilePath(oldEntity.getFilePath());
                entity1.setUpdateDate(LocalDateTime.now());
            } else {
                entity1.setCreatedDate(LocalDateTime.now());
            }
            if (file != null) {
                File ROOT_BASE_PATH = new File(fileUploadPathImage);
                if (!ROOT_BASE_PATH.exists()) {
                    ROOT_BASE_PATH.mkdirs();
                }
                String[] ext = file.getOriginalFilename().split("\\.");
                filePath = fileUploadPathImage + "\\" + new Dates().getCurrentDateTime1() + "-" + new SaveFile().generateRandomString() + "." + ext[ext.length - 1];
                if (isUpdate) {
                    new SaveFile().deleteFile(entity1.getFilePath());
                }
                entity1.setFilePath(filePath);
                new SaveFile().saveFile(file, filePath);
            }
            entity1.setStatusId(eventStatusRfEntity.getId());
            eRepo.save(entity1);


            res.setMessage(isUpdate ? "Event successfully updated." : "Event successfully created.");
            res.setStatusCode(isUpdate ? 200 : 201);
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

        try {
            Optional<EventEntity> eEntityOPtional = eRepo.findById(id);
            EventEntity eventEntity = eEntityOPtional.orElse(null);
            if (eventEntity == null) {
                res.setMessage("Not found");
                res.setStatusCode(404);
                return res;
            }
            Optional<EventStatusRfEntity> esRfEntityOptional = esRepo.findById(eventEntity.getStatusId());
            EventStatusRfEntity eventStatusRfEntity = esRfEntityOptional.orElse(null);
            if (eventStatusRfEntity == null) {
                res.setMessage("Status Not found");
                res.setStatusCode(404);
                return res;
            }
            EventModel eventModel = new EventModel(
                    eventEntity.getId(),
                    eventEntity.getEventName(),
                    eventEntity.getEventType(),
                    eventEntity.getEventStartDate(),
                    eventEntity.getEventEndDate(),
                    eventEntity.getEventLocation(),
                    eventEntity.getAttendance(),
                    eventEntity.getOffering(),
                    eventStatusRfEntity.getStatusName(),
                    eventEntity.getDescription(),
                    0
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

        try {
            String eventPath = Config.getEventImagePath();
            String fileUploadPathImage = eventPath + "\\" + ((DateTimeFormatter.ofPattern("yyyy-MM")).format(LocalDateTime.now()));
            File ROOT_BASE_PATH = new File(fileUploadPathImage);
            if (!ROOT_BASE_PATH.exists()) {
                ROOT_BASE_PATH.mkdirs();
            }
            String[] ext = file.getOriginalFilename().split("\\.");
            String filePath = fileUploadPathImage + "\\" + new Dates().getCurrentDateTime1() + "-" + new SaveFile().generateRandomString() + "." + ext[ext.length - 1];
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

        try {
            Optional<EventEntity> eventEntityOptional = eRepo.findById(id);
            EventEntity eventEntity = eventEntityOptional.orElse(null);
            if (eventEntity == null) {
                res.setMessage("Not found");
                res.setStatusCode(404);
                return res;
            }
            new SaveFile().deleteFile(eventEntity.getFilePath());
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

    @Override
    public ApiResponseModel getUpcomingEvent() {

        try {
            List<EventEntity> entity = eRepo.findUpcomingEvent();
            List<EventModel> eventModel = entity.stream().map(m -> {
                Optional<EventStatusRfEntity> statusOptional = esRepo.findById(m.getStatusId());
                String statusName = statusOptional.map(EventStatusRfEntity::getStatusName).orElse("Unknown");
                return new EventModel(
                        m.getId(),
                        m.getEventName(),
                        m.getEventType(),
                        m.getEventStartDate(),
                        m.getEventEndDate(),
                        m.getEventLocation(),
                        m.getAttendance(),
                        m.getOffering(),
                        statusName,
                        m.getDescription(),
                        0
                );
            }).toList();

            res.setData(eventModel);
            res.setMessage(SUCCESS);
            res.setStatusCode(200);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel getPaginatedEvents(String query, int page, String status) {
        ApiResponseModel res = new ApiResponseModel();
        try {
            System.out.println("Page: " + page);
            String queryFormatted = "%" + query + "%";
            int numberOfRowsToSkip = page == 1 ? 0 : (page - 1) * 10;
            List<ModifiedEventEntity> list = eRepo.paginatedEvents(queryFormatted, numberOfRowsToSkip, status);
            res.setMessage(SUCCESS);
            res.setData(list);
            res.setStatusCode(200);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel getAllEventStatuses() {
        try {
            List<EventStatusRfEntity> list = esRepo.findAll();
            res.setData(list);
            res.setMessage(SUCCESS);
            res.setStatusCode(200);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ApiResponseModel joinEvent(EventPivotEntity entity) {

        try {
            EventPivotEntity epe = epRepo.save(entity);
            return new ApiResponseModel("You successfully joined in this event.", 200, epe);
        } catch (Exception e) {
            return new ApiResponseModel("Can't process your request. Please try again later", 500, null);
        }

    }

    @Override
    public ApiResponseModel leaveEvent(Long id) {

        try {
            Optional<EventPivotEntity> epo = epRepo.findById(id);
            EventPivotEntity ep = epo.orElse(null);
            if (ep == null) return new ApiResponseModel("Unauthorized user. Please re-login your account.", 401, null);
            epRepo.delete(ep);
            return new ApiResponseModel("You successfully leave in this event.", 200, null);
        } catch (Exception e) {
            return new ApiResponseModel("Can't process your request. Please try again later", 500, null);
        }
    }


}
