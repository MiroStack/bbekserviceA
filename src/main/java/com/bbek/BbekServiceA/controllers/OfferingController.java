package com.bbek.BbekServiceA.controllers;

import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.OfferingModel;
import com.bbek.BbekServiceA.serviceImp.OfferingServiceImp;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import static com.bbek.BbekServiceA.util.Constant.BBEK;

@RestController
@RequestMapping(BBEK)
public class OfferingController {
    @Autowired
    OfferingServiceImp serviceImp;
    @GetMapping("getAllOffering")
    ResponseEntity<ApiResponseModel> getAllOffering(){
        try{
            return new ResponseEntity<>(serviceImp.getAllOffering(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("submitOffering")
    ResponseEntity<ApiResponseModel> submitOffering(@RequestBody OfferingModel model, boolean isUpdated, Long id){
        try{

                return new ResponseEntity<>(serviceImp.submitOffering(model, isUpdated), HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getPaymentRf")
    ResponseEntity<ApiResponseModel> getPaymentRf(){
        try{
            return new ResponseEntity<>(serviceImp.getAllPaymentTypeRf(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("getOfferingType")
    ResponseEntity<ApiResponseModel> getOfferingRf(){
        try{
            return new ResponseEntity<>(serviceImp.getAllOfferingTypeRf(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
