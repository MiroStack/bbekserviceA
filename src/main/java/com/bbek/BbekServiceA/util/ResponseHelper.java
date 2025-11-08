package com.bbek.BbekServiceA.util;

import com.bbek.BbekServiceA.model.ApiResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseHelper {
   public ResponseEntity<ApiResponseModel> response (ApiResponseModel res){
        if(res.getStatusCode() == 200) return ResponseEntity.ok(res);
        else if(res.getStatusCode() == 401) return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        else if(res.getStatusCode() == 404) return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        else if(res.getStatusCode() == 400) return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
