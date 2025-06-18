package com.bbek.BbekServiceA.controllers;

import com.bbek.BbekServiceA.entities.MinistryEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.serviceImp.MinistryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.bbek.BbekServiceA.util.Constant.BBEK;

@RestController
@RequestMapping(BBEK)
@CrossOrigin(origins = {"http://localhost:5173/"})
public class MinistryController {
    @Autowired
    MinistryServiceImp serviceImp;

    @GetMapping("getAllMinistry")
    public ResponseEntity<List<MinistryEntity>> getAllMinistry() {
        try {
            return new ResponseEntity<List<MinistryEntity>>(serviceImp.getAllMinistryList(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<List<MinistryEntity>>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/saveMinistry", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseModel> saveMinistry(
            @RequestParam("schedule") String schedule,
            @RequestParam("leader") String leader,
            @RequestParam("statusId") Integer statusId,
            @RequestParam("ministryName") String ministryName,
            @RequestParam("description") String description,
            @RequestParam("member") Integer member,
            @RequestParam("file") MultipartFile file) {
    try{
        MinistryEntity entity = new MinistryEntity();
        entity.setSchedule(schedule);
        entity.setLeader(leader);
        entity.setStatusId(statusId);
        entity.setMinistryName(ministryName);
        entity.setDescription(description);
        entity.setMember(member);
        return new ResponseEntity<>(serviceImp.saveMinistry(entity, file), HttpStatus.OK);
    } catch(RuntimeException e)
    {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}


}
