package com.bbek.BbekServiceA.util;


import com.bbek.BbekServiceA.service.MinistryService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveFile {
    public void saveFile(MultipartFile file, String filePath) throws IOException {

        try {
            byte[] data = file.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, data);

        } catch (IOException ex) {
            Logger.getLogger(MinistryService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public  String generateRandomString() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom RANDOM = new SecureRandom();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
}
