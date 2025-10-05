package com.bbek.BbekServiceA.controllers;

import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.OfferingModel;
import com.bbek.BbekServiceA.serviceImp.MemberServiceImp;
import com.bbek.BbekServiceA.serviceImp.OfferingServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bbek.BbekServiceA.util.Constant.BBEK;

@RestController
@RequestMapping(BBEK)
public class MemberController {
    @Autowired
    MemberServiceImp serviceImp;
    @GetMapping("getMembers")
    ResponseEntity<ApiResponseModel> getMembers(@RequestParam(value = "query", required = false)String query, @RequestParam(value = "page", required = false) int page){
        try{
            return new ResponseEntity<>(serviceImp.getMemberPage(query, page), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
