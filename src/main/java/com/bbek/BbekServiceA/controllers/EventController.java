package com.bbek.BbekServiceA.controllers;

import com.bbek.BbekServiceA.entities.EventEntity;
import com.bbek.BbekServiceA.entities.MinistryEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.EventModel;
import com.bbek.BbekServiceA.model.MinistryModel;
import com.bbek.BbekServiceA.service.EventService;
import com.bbek.BbekServiceA.serviceImp.EventServiceImp;
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
import java.util.List;

import static com.bbek.BbekServiceA.util.Constant.BBEK;

@RestController
@RequestMapping(BBEK)
@CrossOrigin(origins = {"http://localhost:5173/"})
public class EventController {
    @Autowired
    EventServiceImp eService;
    @PostMapping(value = "/saveEvent", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseModel> saveMinistry(
            @RequestParam("eventName") String eventName,
            @RequestParam("eventType") String eventType,
            @RequestParam("eventDate") String eventDate,
            @RequestParam("eventTime") String eventTime,
            @RequestParam("eventLocation") String eventLocation,
            @RequestParam("attendance") int attendance,
            @RequestParam("offering") int offering,
            @RequestParam("status_id") int status_id,
            @RequestParam("file") MultipartFile file) {
        try{
            EventEntity entity = new EventEntity();
            entity.setEventName(eventName);
            entity.setEventType(eventType);
            entity.setEventDate(eventDate);
            entity.setEvent_time(eventTime);
            entity.setEventLocation(eventLocation);
            entity.setAttendance(attendance);
            entity.setOffering(offering);
            entity.setStatusId(status_id);
            return new ResponseEntity<>(eService.saveEvent(entity, file), HttpStatus.OK);
        } catch(RuntimeException e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getAllEvent")
    public ResponseEntity<List<EventModel>> getAllEventList() {
        try {
            return new ResponseEntity<List<EventModel>>(eService.getAllevent(), HttpStatus.OK);
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

}
