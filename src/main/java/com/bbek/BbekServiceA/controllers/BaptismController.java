package com.bbek.BbekServiceA.controllers;

import com.bbek.BbekServiceA.entities.BaptismEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.LoginRequestModel;
import com.bbek.BbekServiceA.model.RegistrationModel;
import com.bbek.BbekServiceA.model.RegistrationRequestModel;
import com.bbek.BbekServiceA.model.baptism.AddBaptismRequestModel;
import com.bbek.BbekServiceA.model.baptism.BaptismScheduleModel;
import com.bbek.BbekServiceA.serviceImp.BaptismServiceImp;
import com.bbek.BbekServiceA.serviceImp.EmailSenderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.bbek.BbekServiceA.util.Constant.BBEK;
import static com.bbek.BbekServiceA.util.Constant.SUCCESS;

@RestController
@RequestMapping(BBEK)
public class BaptismController {
    @Autowired
    BaptismServiceImp serviceImp;
    @Autowired
    EmailSenderServiceImp emailSenderServiceImp;

    @PostMapping("submitBaptism")
    public ResponseEntity<ApiResponseModel> submitBaptism(@RequestBody RegistrationRequestModel model) {
        try {
            return new ResponseEntity<>(serviceImp.submitBaptismRequest(model), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getBaptism")
    public ResponseEntity<ApiResponseModel> getBaptism(@RequestParam(value = "query", required = false) String query, @RequestParam(value = "page", required = false) int page) {
        try {
            return ResponseEntity.ok(serviceImp.getPaginatedBaptism(query, page));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("getOfficiants")
    public ResponseEntity<ApiResponseModel> getOfficiants() {
        try {
            return ResponseEntity.ok(serviceImp.getBaptismOfficiants());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getCertificateStatuses")
    public ResponseEntity<ApiResponseModel> getCertificateStatuses() {
        try {
            return ResponseEntity.ok(serviceImp.getCertificateStatuses());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getBaptismStatuses")
    public ResponseEntity<ApiResponseModel> getBaptismStatuses() {
        try {
            return ResponseEntity.ok(serviceImp.getBaptismStatuses());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/sendEmailMessage")
    public ResponseEntity<ApiResponseModel> sendEmailMessage(@RequestParam("subject") String subject,
                                                             @RequestParam("message") String message,
                                                             @RequestParam("email") String email) {
        ApiResponseModel res = new ApiResponseModel();
        try {
            emailSenderServiceImp.sendEmailMessage(email, message, subject);
            res.setMessage(SUCCESS);
            res.setStatusCode(200);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/scheduleBaptism")
    public ResponseEntity<ApiResponseModel> scheduleBaptism(@RequestBody BaptismScheduleModel model){
        try {
            return ResponseEntity.ok(serviceImp.sentBaptismSchedule(model));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ApiResponseModel(e.getMessage(), 500, ""),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("addBaptism")
    public ResponseEntity<ApiResponseModel> addBaptism(@RequestBody AddBaptismRequestModel model){
        ApiResponseModel res = new ApiResponseModel();
        try{
            res= serviceImp.addBaptism(model);
            if(res.getStatusCode()==400){return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);}
            return ResponseEntity.ok(res);

        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponseModel("Failed to create baptism schedule. Please try again", 500, ""), HttpStatus.BAD_REQUEST);
        }
    }
}
