package com.bbek.BbekServiceA.controllers;

import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.MarriageResponseModel;
import com.bbek.BbekServiceA.serviceImp.MarriageServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bbek.BbekServiceA.util.Constant.BBEK;

@RestController
@RequestMapping(BBEK)
@CrossOrigin(origins = {"http://localhost:5173/"})
public class MarriageController {
    @Autowired
    MarriageServiceImp serviceImp;

    @GetMapping("getAllMarriageStatuses")
    public ResponseEntity<ApiResponseModel> getAllMarriageStatuses(){
        try{
            return new ResponseEntity<>(serviceImp.getAllMarriageStatuses(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("getAllMarriageLocations")
    public ResponseEntity<ApiResponseModel> getAllMarriageLocations(){
        try{
            return new ResponseEntity<>(serviceImp.getAllLocations(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("getAllMarriageRecord")
    public ResponseEntity<ApiResponseModel> getAllMarriageRecord(){
        try{
            return new ResponseEntity<>(serviceImp.getAllMarriages(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("deleteMarriageRecord")
    public ResponseEntity<ApiResponseModel> deleteMarriageRecord(@RequestParam("id")Long id){
        try{
            return new ResponseEntity<>(serviceImp.deleteMarriage(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("submitMarriageRecord")
    public ResponseEntity<ApiResponseModel> submitMarriageRecord(@RequestBody MarriageResponseModel model){
        try{
            return new ResponseEntity<>(serviceImp.saveMarriages(model.getEntity(), model.isUpdate()), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
