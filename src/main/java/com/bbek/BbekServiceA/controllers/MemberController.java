package com.bbek.BbekServiceA.controllers;

import com.bbek.BbekServiceA.entities.MemberDetailsEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.OfferingModel;
import com.bbek.BbekServiceA.model.baptism.AddBaptismRequestModel;
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
    ResponseEntity<ApiResponseModel> getMembers(@RequestParam(value = "query", required = false) String query, @RequestParam(value = "page", required = false) int page) {
        try {
            return new ResponseEntity<>(serviceImp.getMemberPage(query, page), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("addMember")
    public ResponseEntity<ApiResponseModel> addMember(@RequestBody AddBaptismRequestModel model) {
        ApiResponseModel res = new ApiResponseModel();
        try {
            res = serviceImp.addMember(model);
            if (res.getStatusCode() == 400) {
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok(res);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponseModel("Failed to create account for member. Please try again", 500, ""), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("viewDetails/{memberId}")
    public ResponseEntity<ApiResponseModel> viewDetails(@PathVariable("memberId") Long memberId) {
        ApiResponseModel res = serviceImp.viewDetails(memberId);
        try {
            if (res.getStatusCode() == 404) return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
            return ResponseEntity.ok(res);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponseModel("Something went wrong. Please try again!", 500, ""), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("departments")
    public ResponseEntity<ApiResponseModel> departments() {
        try {
            return ResponseEntity.ok(serviceImp.getDepartmentList());
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponseModel("Something went wrong. Please try again!", 500, ""), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("positions")
    public ResponseEntity<ApiResponseModel> positions() {
        try {
            return ResponseEntity.ok(serviceImp.getPositionList());
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponseModel("Something went wrong. Please try again!", 500, ""), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("editMemberDetails")
    public ResponseEntity<ApiResponseModel> editMemberDetails(@RequestBody MemberDetailsEntity entity) {
        try {
            ApiResponseModel res = serviceImp.editMemberDetails(entity);
            if (res.getStatusCode() == 200) return ResponseEntity.ok(res);
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponseModel("Something went wrong. Please try again!", 500, ""), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("ListOfPriest")
    public ResponseEntity<ApiResponseModel> listOfPriest(@RequestParam(value = "query", defaultValue = "") String query, @RequestParam(value = "page", defaultValue = "1") int page){
        try{
            ApiResponseModel res = serviceImp.fetchPriestMembers(query, page);
            if(res.getStatusCode() != 200) return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponseModel("Something went wrong. Please try again", 500, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
