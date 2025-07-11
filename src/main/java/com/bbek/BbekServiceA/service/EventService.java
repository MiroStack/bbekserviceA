package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.entities.EventEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.EventModel;
import com.bbek.BbekServiceA.model.MinistryModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventService {
    public List<EventModel> getAllevent();
   ApiResponseModel saveEvent(
          EventEntity entity,
          MultipartFile file
//        String eventName,
//        String eventType,
//        String eventDate,
//        String eventTime,
//        String eventLocation,
//        int attendance,
//        int offering,
//        int status_id,
//        String filepath
   );
    public String getEventImage(String eventName);
}
