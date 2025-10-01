package com.bbek.BbekServiceA.controllers;

import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.LoginRequestModel;
import com.bbek.BbekServiceA.model.RegistrationRequestModel;
import com.bbek.BbekServiceA.service.AuthService;
import com.bbek.BbekServiceA.serviceImp.JWTService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.bbek.BbekServiceA.util.Constant.BBEK;
import static com.bbek.BbekServiceA.util.Constant.SUCCESS;

@RestController
@RequestMapping(BBEK)
@CrossOrigin(origins = {"http://localhost:5173/"})
public class AuthController {
    @Autowired
    AuthService service;

    @Autowired
    JWTService jwtService;
    @PostMapping("login")
    public ResponseEntity<ApiResponseModel> login(@RequestBody LoginRequestModel model){
        try{
            return new ResponseEntity<ApiResponseModel>(service.login(model.getUsername(), model.getPassword()), HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("register")
    public ResponseEntity<ApiResponseModel> register(@RequestBody RegistrationRequestModel model){
        try{
            return new ResponseEntity<ApiResponseModel>(service.register(model), HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<ApiResponseModel> validateToken(@RequestParam String token) {
        ApiResponseModel res = new ApiResponseModel();
        Claims claims = jwtService.extractUserClaims(token);
        if (claims == null) {
            res.setStatusCode(401);
            res.setMessage("Invalid Token");
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }
        String username = claims.get("username", String.class);



        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", username);
        res.setStatusCode(200);
        res.setMessage(SUCCESS);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
