package com.bbek.BbekServiceA.controllers;

import com.bbek.BbekServiceA.entities.ministries.MinistryEntity;
import com.bbek.BbekServiceA.entities.pivot.MinistryPivotEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.ministry.MinistryModel;
import com.bbek.BbekServiceA.model.TokenModel;
import com.bbek.BbekServiceA.service.AuthService;
import com.bbek.BbekServiceA.serviceImp.MinistryServiceImp;
import com.bbek.BbekServiceA.util.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.List;

import static com.bbek.BbekServiceA.util.Constant.BBEK;
import static com.bbek.BbekServiceA.util.Constant.SUCCESS;

@RestController
@RequestMapping(BBEK)
@CrossOrigin(origins = {"http://localhost:5173/"})
public class MinistryController {
    @Autowired
    MinistryServiceImp serviceImp;

    @Autowired
    ResponseHelper helper;

    @Autowired
    AuthService authService;

    @GetMapping("getAllMinistry")
    public ResponseEntity<List<MinistryModel>> getAllMinistry(@RequestParam(value = "query", required = false) String query, @RequestParam(value = "page", required = false) int page) {
        try {
            return new ResponseEntity<>(serviceImp.getAllMinistryList(query, page), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
            @RequestParam("department") String department,
            @RequestParam("startTime") LocalTime startTime,
            @RequestParam("endTime") LocalTime endTime,
            @RequestParam("isUpdate") boolean isUpdate,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            MinistryEntity entity = new MinistryEntity();
            if (isUpdate) {
                entity.setId(id);
            }
            entity.setSchedule(schedule);
            entity.setLeader(leader);
            entity.setMinistryName(ministryName);
            entity.setDescription(description);
            entity.setMember(member);
            entity.setStartTime(startTime);
            entity.setEndTime(endTime);
            return new ResponseEntity<>(serviceImp.saveMinistry(entity, isUpdate, statusName, department, file), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getMinistry")
    public ResponseEntity<ApiResponseModel> getMinistry(Long id) {
        try {
            return new ResponseEntity<>(serviceImp.getMinistry(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("deleteMinistry")
    public ResponseEntity<ApiResponseModel> deleteMinistry(Long id) {
        try {
            return new ResponseEntity<>(serviceImp.deleteMinistry(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getUpcomingMinistry")
    public ResponseEntity<ApiResponseModel> getUpcomingMinistry() {
        try {
            return new ResponseEntity<>(serviceImp.getUpcomingMinistry(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getPaginatedMinistry")
    public ResponseEntity<ApiResponseModel> getPaginatedMinistry(@RequestParam(value = "query", required = false) String query, @RequestParam(value = "page", required = false) int page) {
        try {
            return new ResponseEntity<>(serviceImp.getPaginatedMinistry(query, page), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("getMinistryStatuses")
    public ResponseEntity<ApiResponseModel> getMinistryStatuses() {
        try {
            return new ResponseEntity<>(serviceImp.getMinistryStatuses(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("joinMinistry")
    public ResponseEntity<ApiResponseModel> joinMinistry(@RequestBody MinistryPivotEntity entity) {
        ApiResponseModel res = serviceImp.joinMinistry(entity);
        return helper.response(res);
    }

    @GetMapping("leaveMinistry/{id}")
    public ResponseEntity<ApiResponseModel> leaveMinistry(@PathVariable("id") Long id) {
        ApiResponseModel res = serviceImp.leaveMinistry(id);
        return helper.response(res);
    }

    @GetMapping("ministriesOfAllLadies")
    List<MinistryModel> ministriesOfAllLadiesDepartment(@RequestParam("query") String query, @RequestParam("page") int page){
        return serviceImp.getAllLadiesMinistries(query, page);
    }
    @GetMapping("ministriesOfAllMen")
    List<MinistryModel> ministriesOfAllMenDepartment(@RequestParam("query") String query, @RequestParam("page") int page){
        return serviceImp.getAllMenMinistries(query, page);
    }
    @GetMapping("ministriesOfAllYoungPeople")
    List<MinistryModel> ministriesOfAllYoungPeopleDepartment(@RequestParam("query") String query, @RequestParam("page") int page){
        return serviceImp.getYoungPeopleMinistries(query, page);
    }

    @GetMapping("ministriesOfUser")
    ResponseEntity<ApiResponseModel> getUserMinistries(HttpServletRequest request, @RequestParam("query") String query, @RequestParam("page") int page){
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            TokenModel model = authService.getTokenModel(token);
            String message = "Extracted token: " + token;// remove "Bearer "
            List<MinistryModel> list = serviceImp.getMyMinistry(query, page, model.getMemberId());
            if(list.isEmpty())return ResponseEntity.ok(new ApiResponseModel("No ministries found.", 404, null));
            return  ResponseEntity.ok(new ApiResponseModel(SUCCESS, 200, list));
        } else {
            String message = "No Bearer token found";
            return ResponseEntity.ok(new ApiResponseModel(message, 401, null));
        }

    }

    public String sanitize(String input) {
        return input.replaceAll("[^a-zA-Z0-9-_]", " ");
    }

    @PostMapping("joinMinistry")
    ResponseEntity<ApiResponseModel> joinMinistry(HttpServletRequest request, @RequestParam("ministryId") Long ministryId){
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            TokenModel model = authService.getTokenModel(token);
            String message = "Extracted token: " + token;// remove "Bearer "
            MinistryPivotEntity entity = new MinistryPivotEntity();
            entity.setMinistryId(ministryId);
            entity.setMemberId(model.getMemberId());
            ApiResponseModel res = serviceImp.joinMinistry(entity);
            return helper.response(res);
        } else {
            String message = "No Bearer token found";
            return ResponseEntity.ok(new ApiResponseModel(message, 400, null));
        }
    }


}
