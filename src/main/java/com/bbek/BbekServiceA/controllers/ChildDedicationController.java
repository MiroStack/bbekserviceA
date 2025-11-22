package com.bbek.BbekServiceA.controllers;

import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.TokenModel;
import com.bbek.BbekServiceA.model.dedication.ChildDedicationReqModel;
import com.bbek.BbekServiceA.service.AuthService;
import com.bbek.BbekServiceA.serviceImp.AuthServiceImp;
import com.bbek.BbekServiceA.serviceImp.ChildDedicationServiceImp;
import com.bbek.BbekServiceA.util.ResponseHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.bbek.BbekServiceA.util.Constant.BBEK;

@RestController
@RequestMapping(BBEK)
public class ChildDedicationController {
    @Autowired
    ChildDedicationServiceImp serviceImp;
    @Autowired
    ResponseHelper helper;
    @Autowired
    AuthServiceImp authServiceImp;

    @PostMapping("create_child_dedication")
    ResponseEntity<ApiResponseModel> createChildDedication(
            @RequestBody ChildDedicationReqModel model
    ) {

        ApiResponseModel res = serviceImp.requestChildDedication(
                model.getMemberId(),
                model.getChildName(),
                model.getGuardianName(),
                model.getBirthdate(),
                model.getDedicationDt(),
                model.getContactNo()
        );
        return helper.responseHelper(res);

    }

    @PutMapping("update_child_dedication")
    ResponseEntity<ApiResponseModel> updateChildDedication(
            HttpServletRequest request,
            @RequestBody ChildDedicationReqModel model
    ) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            TokenModel tokenModel = authServiceImp.getTokenModel(token);
            String message = "Extracted token: " + token;// remove "Bearer "

            ApiResponseModel res = serviceImp.updateChildDedication(
                    model.getReqId(),
                    tokenModel.getMemberId(),
                    model.getChildName(),
                    model.getGuardianName(),
                    model.getBirthdate(),
                    model.getDedicationDt(),
                    model.getContactNo(),
                    model.getStatusId());
            return helper.responseHelper(res);
        } else {
            String message = "No Bearer token found";
            return ResponseEntity.ok(new ApiResponseModel(message, 400, null));
        }


    }

    @GetMapping("get_all_child_dedication")
    ResponseEntity<ApiResponseModel> getAllChildDedication(@RequestParam(value = "query", defaultValue = "") String query, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "statusName", defaultValue = "") String statusName) {
        ApiResponseModel res = serviceImp.listOfChildDedication(query, page, statusName);
        return helper.responseHelper(res);
    }

    @GetMapping("get_child_dedication_kpi")
    ResponseEntity<ApiResponseModel> getChildDedicationKPI() {
        ApiResponseModel res = serviceImp.getKPI();
        return helper.responseHelper(res);
    }
}
