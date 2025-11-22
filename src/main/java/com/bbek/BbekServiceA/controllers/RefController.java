package com.bbek.BbekServiceA.controllers;

import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.serviceImp.RefServiceImp;
import com.bbek.BbekServiceA.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bbek.BbekServiceA.util.Constant.BBEK;

@RestController
@RequestMapping(BBEK)
public class RefController {
    @Autowired
    RefServiceImp serviceImp;

    @Autowired
    ResponseHelper helper;
    @GetMapping("service_statuses")
    ResponseEntity<ApiResponseModel> getAllServiceStatuses(){
        ApiResponseModel res = serviceImp.findAllServiceStatus();
        return helper.responseHelper(res);
    }
}
