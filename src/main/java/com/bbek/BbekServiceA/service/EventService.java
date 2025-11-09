package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.entities.EventEntity;
import com.bbek.BbekServiceA.entities.pivot.EventPivotEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.event.EventModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventService {
    public List<EventModel> getAllevent(String query, int page, String status);
    List<EventModel> getAllUserEvent(String query, int page, String status, Long memberId);
    List<EventModel> viewMembersOfEvents(String query, int page, String status, Long eventId);
   ApiResponseModel saveEvent(
          EventEntity entity,
          MultipartFile file,
          boolean isUpdate,
          String statusName
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
   ApiResponseModel getEvent(Long id);
   ApiResponseModel updateEvent(
           EventEntity entity,
           MultipartFile file
   );
   ApiResponseModel deleteEvent(Long id);
    public String getEventImage(String eventName);

    ApiResponseModel getUpcomingEvent();
    ApiResponseModel getPaginatedEvents(String query, int numberOfRowsToSkip, String status);
    ApiResponseModel getAllEventStatuses();
    ApiResponseModel joinEvent(EventPivotEntity entity);
    ApiResponseModel leaveEvent(Long id);
    ApiResponseModel updateMemberJoinEventApplication(Long pivotId, String statusName, Long userId);
    ApiResponseModel viewMembersOfEvents(Long eventId, String query, int page);
    ApiResponseModel viewTotalMemberPerEvent(Long eventId);

}
