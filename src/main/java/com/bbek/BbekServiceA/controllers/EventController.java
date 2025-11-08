package com.bbek.BbekServiceA.controllers;

import com.bbek.BbekServiceA.entities.EventEntity;
import com.bbek.BbekServiceA.entities.pivot.EventPivotEntity;
import com.bbek.BbekServiceA.entities.pivot.MinistryPivotEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.MinistryModel;
import com.bbek.BbekServiceA.model.TokenModel;
import com.bbek.BbekServiceA.model.event.EventModel;
import com.bbek.BbekServiceA.service.AuthService;
import com.bbek.BbekServiceA.serviceImp.EventServiceImp;
import com.bbek.BbekServiceA.util.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import static com.bbek.BbekServiceA.util.Constant.BBEK;
import static com.bbek.BbekServiceA.util.Constant.SUCCESS;

@RestController
@RequestMapping(BBEK)
@CrossOrigin(origins = {"http://localhost:5173/"})
public class EventController {
    @Autowired
    EventServiceImp eService;

    @Autowired
    ResponseHelper helper;

    @Autowired
    AuthService authService;

    @PostMapping(value = "/saveEvent", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseModel> saveMinistry(
            @RequestParam("id") long id,
            @RequestParam("eventName") String eventName,
            @RequestParam("eventType") String eventType,
            @RequestParam("eventStartDate") String eventStartDate,
            @RequestParam("eventEndDate") String eventEndDate,
            @RequestParam("eventLocation") String eventLocation,
            @RequestParam("attendance") int attendance,
            @RequestParam("offering") int offering,
            @RequestParam("description") String description,
            @RequestParam("statusName") String statusName,
            @RequestParam("isUpdate") boolean isUpdate,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        try {
            EventEntity entity = new EventEntity();
            if (isUpdate) {
                entity.setId(id);
            }
            entity.setEventName(eventName);
            entity.setEventType(eventType);
            entity.setEventStartDate(LocalDateTime.parse(eventStartDate));
            entity.setEventEndDate(LocalDateTime.parse(eventEndDate));
            entity.setEventLocation(eventLocation);
            entity.setAttendance(attendance);
            entity.setOffering(offering);
            entity.setDescription(description);

            return new ResponseEntity<>(eService.saveEvent(entity, file, isUpdate, statusName), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getAllEvent")
    public ResponseEntity<List<EventModel>> getAllEventList(@RequestParam(value = "query", required = false) String query, @RequestParam(value = "page", required = false) int page, @RequestParam(value = "status") String status) {
        try {
            return new ResponseEntity<List<EventModel>>(eService.getAllevent(query, page, status), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<List<EventModel>>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/event_image")
    public ResponseEntity<byte[]> getImage(@RequestParam String eventName) throws IOException {
//        String sanitizedName = sanitize(eventName);
        String filename = eService.getEventImage(eventName);

        if (filename == null || filename.isBlank()) {
            return ResponseEntity.notFound().build(); // 404 if no filepath found
        }

        Path baseDir = Paths.get("C:/BBEK/FILES/").toAbsolutePath().normalize();
        Path imagePath = baseDir.resolve(filename).normalize();

        if (!imagePath.startsWith(baseDir) || !Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }

        byte[] imageBytes = Files.readAllBytes(imagePath);
        String contentType = Files.probeContentType(imagePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"));

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("getEvent")
    public ResponseEntity<ApiResponseModel> getEvent(@RequestParam("id") long id) {
        try {
            return new ResponseEntity<>(eService.getEvent(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("deleteEvent")
    public ResponseEntity<ApiResponseModel> deleteEvent(@RequestParam("id") Long id) {
        try {
            return new ResponseEntity<>(eService.deleteEvent(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("upcomingEvents")
    public ResponseEntity<ApiResponseModel> upcomingEvents() {
        try {
            return new ResponseEntity<>(eService.getUpcomingEvent(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getPaginatedEvents")
    public ResponseEntity<ApiResponseModel> getPaginatedEvents(@RequestParam(value = "query", required = false) String query, @RequestParam(value = "page", required = false) int page, String status) {
        try {
            return new ResponseEntity<>(eService.getPaginatedEvents(query, page, status), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }

    @GetMapping("getAllEventStatuses")
    public ResponseEntity<ApiResponseModel> getAllEventStatuses() {
        try {
            return new ResponseEntity<>(eService.getAllEventStatuses(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }

    @GetMapping("joinEvent")
    public ResponseEntity<ApiResponseModel> joinEvent(@RequestBody EventPivotEntity entity) {
        ApiResponseModel res = eService.joinEvent(entity);
        return helper.response(res);
    }

    @GetMapping("leaveEvent/{id}")
    public ResponseEntity<ApiResponseModel> leaveEvent(@PathVariable("id") Long id) {
        ApiResponseModel res = eService.leaveEvent(id);
        return helper.response(res);
    }

    @PostMapping("joinEvent")
    ResponseEntity<ApiResponseModel> joinEvent(HttpServletRequest request, @RequestParam("eventId") Long eventId) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            TokenModel model = authService.getTokenModel(token);
            String message = "Extracted token: " + token;// remove "Bearer "
            EventPivotEntity entity = new EventPivotEntity();
            entity.setEventId(eventId);
            entity.setMemberId(model.getMemberId());
            ApiResponseModel res = eService.joinEvent(entity);
            return helper.response(res);
        } else {
            String message = "No Bearer token found";
            return ResponseEntity.ok(new ApiResponseModel(message, 400, null));
        }
    }

    @GetMapping("eventsOfUser")
    ResponseEntity<ApiResponseModel> eventsOfUser(HttpServletRequest request, @RequestParam(value = "query", required = false) String query, @RequestParam(value = "page", required = false) int page, @RequestParam("status") String status) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            TokenModel model = authService.getTokenModel(token);
            String message = "Extracted token: " + token;// remove "Bearer "
            List<EventModel> list = eService.getAllUserEvent(query, page, status, model.getMemberId());
            if (list.isEmpty()) return ResponseEntity.ok(new ApiResponseModel("No event found.", 404, null));
            return ResponseEntity.ok(new ApiResponseModel(SUCCESS, 200, list));
        } else {
            String message = "No Bearer token found";
            return ResponseEntity.ok(new ApiResponseModel(message, 401, null));
        }
    }

    @GetMapping("viewMembersOfEvent")
    ResponseEntity<ApiResponseModel> viewMembersOfEvent(@RequestParam(value = "query", required = false) String query, @RequestParam(value = "page", required = false) int page, @RequestParam("status") String status, @RequestParam("eventId") Long eventId) {

        List<EventModel> list = eService.viewMembersOfEvents(query, page, status, eventId);
        if (list.isEmpty()) return ResponseEntity.ok(new ApiResponseModel("No event found.", 404, null));
        return ResponseEntity.ok(new ApiResponseModel(SUCCESS, 200, list));
    }


}
