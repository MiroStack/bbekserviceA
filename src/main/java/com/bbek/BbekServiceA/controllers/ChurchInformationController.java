package com.bbek.BbekServiceA.controllers;

import com.bbek.BbekServiceA.entities.church_information.ChurchInformationEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.TokenModel;
import com.bbek.BbekServiceA.model.information.ChurchInformationReqModel;
import com.bbek.BbekServiceA.service.ChurchInformationService;
import com.bbek.BbekServiceA.serviceImp.AuthServiceImp;
import com.bbek.BbekServiceA.serviceImp.ChurchInformationServiceImp;
import com.bbek.BbekServiceA.util.ResponseHelper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static com.bbek.BbekServiceA.util.Constant.BBEK;

@RestController
@RequestMapping(BBEK)
public class ChurchInformationController {
    @Autowired
    ResponseHelper helper;
    @Autowired
    AuthServiceImp authServiceImp;
    @Autowired
    ChurchInformationServiceImp informationService;
    @GetMapping("view_church_information")
    ResponseEntity<ApiResponseModel> viewChurchInformation(){
        ApiResponseModel res = informationService.viewChurchInformation();
        return helper.responseHelper(res);
    }
    @PostMapping(value = "save_church_information",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ApiResponseModel> savedChurchInformation(HttpServletRequest request, @ModelAttribute ChurchInformationReqModel model){
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            TokenModel tokenModel = authServiceImp.getTokenModel(token);
            String message = "Extracted token: " + token;// remove "Bearer "
            ChurchInformationEntity churchInformationEntity = new ChurchInformationEntity();
            churchInformationEntity.setId(1L);
            if (model.getChurchName() != null && !model.getChurchName().isEmpty()) {
                churchInformationEntity.setChurchName(model.getChurchName());
            }

            if (model.getAcronym() != null && !model.getAcronym().isEmpty()) {
                churchInformationEntity.setAcronym(model.getAcronym());
            }

            if (model.getAddress() != null && !model.getAddress().isEmpty()) {
                churchInformationEntity.setAddress(model.getAddress());
            }

            if (model.getEmail() != null && !model.getEmail().isEmpty()) {
                churchInformationEntity.setEmail(model.getEmail());
            }

            if (model.getMission() != null && !model.getMission().isEmpty()) {
                churchInformationEntity.setMission(model.getMission());
            }

            if (model.getVision() != null && !model.getVision().isEmpty()) {
                churchInformationEntity.setVision(model.getVision());
            }

            if (model.getWebsite() != null && !model.getWebsite().isEmpty()) {
                churchInformationEntity.setWebsite(model.getWebsite());
            }

            if (model.getPhone() != null && !model.getPhone().isEmpty()) {
                churchInformationEntity.setPhone(model.getPhone());
            }
            churchInformationEntity.setModifiedById(tokenModel.getMemberId());
            churchInformationEntity.setModifiedDt(LocalDateTime.now());
            ApiResponseModel res = informationService.saveChurchInformation(churchInformationEntity, model.getFile());
            return helper.responseHelper(res);

        } else {
            String message = "No Bearer token found";
            return ResponseEntity.ok(new ApiResponseModel(message, 400, null));
        }

    }
    @GetMapping("view_logo")
    public ResponseEntity<byte[]> getLogoImage() throws IOException {
        String filename = informationService.viewLogo();

        if (filename == null || filename.isBlank()) {
            return ResponseEntity.notFound().build(); // 404 if no filepath found
        }

        Path baseDir = Paths.get("C:/BBEK/FILES/").toAbsolutePath().normalize();
        Path imagePath = baseDir.resolve(filename).normalize();

        if (!imagePath.startsWith(baseDir) || !Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }

        byte[] imageBytes = Files.readAllBytes(imagePath);
        String contentType = Files.probeContentType(imagePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"));

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}
