package com.bbek.BbekServiceA.controllers;

import com.bbek.BbekServiceA.entities.BaptismEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.LoginRequestModel;
import com.bbek.BbekServiceA.model.RegistrationModel;
import com.bbek.BbekServiceA.model.RegistrationRequestModel;
import com.bbek.BbekServiceA.serviceImp.BaptismServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bbek.BbekServiceA.util.Constant.BBEK;

@RestController
@RequestMapping(BBEK)
public class BaptismController {
    @Autowired
    BaptismServiceImp serviceImp;
    @PostMapping("submitBaptism")
    public ResponseEntity<ApiResponseModel> submitBaptism(@RequestBody RegistrationRequestModel model){
        try{
            return new ResponseEntity<>(serviceImp.submitBaptismRequest(model), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getBaptism")
    public ResponseEntity<ApiResponseModel> getBaptism(@RequestParam(value = "query", required = false) String query, @RequestParam(value = "page", required = false) int page){
        try{
            return ResponseEntity.ok(serviceImp.getPaginatedBaptism(query, page));
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
