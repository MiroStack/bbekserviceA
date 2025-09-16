package com.bbek.BbekServiceA.controllers;

import com.bbek.BbekServiceA.entities.MinistryEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.MinistryModel;
import com.bbek.BbekServiceA.serviceImp.MinistryServiceImp;
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
import java.time.LocalTime;
import java.util.List;

import static com.bbek.BbekServiceA.util.Constant.BBEK;

@RestController
@RequestMapping(BBEK)
@CrossOrigin(origins = {"http://localhost:5173/"})
public class MinistryController {
    @Autowired
    MinistryServiceImp serviceImp;

    @GetMapping("getAllMinistry")
    public ResponseEntity<List<MinistryModel>> getAllMinistry() {
        try {
            return new ResponseEntity<List<MinistryModel>>(serviceImp.getAllMinistryList(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<List<MinistryModel>>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/ministry_image")
    public ResponseEntity<byte[]> getImage(@RequestParam String ministryName) throws IOException {
//        String sanitizedName = sanitize(ministryName);
        String filename = serviceImp.getMinistryImage(ministryName);

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





    @PostMapping(value = "/saveMinistry", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseModel> saveMinistry(
            @RequestParam("id") long id,
            @RequestParam("schedule") String schedule,
            @RequestParam("leader") String leader,
            @RequestParam("statusName") String statusName,
            @RequestParam("ministryName") String ministryName,
            @RequestParam("description") String description,
            @RequestParam("member") Integer member,
            @RequestParam("startTime") LocalTime startTime,
            @RequestParam("endTime") LocalTime endTime,
            @RequestParam("isUpdate") boolean isUpdate,
            @RequestParam(value = "file", required = false) MultipartFile file) {
    try{
        MinistryEntity entity = new MinistryEntity();
        if(isUpdate){
            entity.setId(id);
        }
        entity.setSchedule(schedule);
        entity.setLeader(leader);
        entity.setMinistryName(ministryName);
        entity.setDescription(description);
        entity.setMember(member);
        entity.setStartTime(startTime);
        entity.setEndTime(endTime);
        return new ResponseEntity<>(serviceImp.saveMinistry(entity,  isUpdate, statusName, file), HttpStatus.OK);
    } catch(RuntimeException e)
    {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
   @GetMapping("getMinistry")
   public ResponseEntity<ApiResponseModel> getMinistry(Long id){
        try{
            return new ResponseEntity<>(serviceImp.getMinistry(id),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
   }

    @DeleteMapping("deleteMinistry")
    public ResponseEntity<ApiResponseModel> deleteMinistry(Long id){
        try{
            return new ResponseEntity<>(serviceImp.deleteMinistry(id),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getUpcomingMinistry")
    public ResponseEntity<ApiResponseModel> getUpcomingMinistry(){
        try{
            return new ResponseEntity<>(serviceImp.getUpcomingMinistry(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getPaginatedMinistry")
    public ResponseEntity<ApiResponseModel> getPaginatedMinistry(@RequestParam(value = "query", required = false)String query, @RequestParam(value = "index", required = false)int index){
        try{
            return new ResponseEntity<>(serviceImp.getPaginatedEvents(query, index), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



    public String sanitize(String input) {
        return input.replaceAll("[^a-zA-Z0-9-_]", " ");
    }


}
