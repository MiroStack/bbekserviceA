package com.bbek.BbekServiceA.controllers;

import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.LoginRequestModel;
import com.bbek.BbekServiceA.model.RegistrationRequestModel;
import com.bbek.BbekServiceA.service.AuthService;
import com.bbek.BbekServiceA.serviceImp.JWTService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.bbek.BbekServiceA.util.Constant.BBEK;

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
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        Claims claims = jwtService.extractUserClaims(token);
        if (claims == null) {
            return ResponseEntity.status(401).body("Invalid Token");
        }
        String username = claims.get("username", String.class);
        String fullname = claims.get("fullname", String.class);
        String role = claims.get("role", String.class);


        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", username);
        userInfo.put("role", role);
        userInfo.put("fullname", fullname);
        return ResponseEntity.ok(userInfo);
    }
}
