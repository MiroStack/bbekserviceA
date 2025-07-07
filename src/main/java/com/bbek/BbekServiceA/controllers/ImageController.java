package com.bbek.BbekServiceA.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.bbek.BbekServiceA.util.Constant.BBEK;

@RequestMapping(BBEK)
@RestController
public class ImageController {
    @GetMapping("{filename}")
    public ResponseEntity<byte[]> getImage(@RequestParam String filename) {
        try {
            // Path to your image directory
            Path imagePath = Paths.get( filename);

            byte[] imageBytes = Files.readAllBytes(imagePath);

            // Automatically determine content type (e.g., image/jpeg, image/png)
            String contentType = Files.probeContentType(imagePath);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentLength(imageBytes.length);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
