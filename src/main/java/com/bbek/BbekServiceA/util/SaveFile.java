package com.bbek.BbekServiceA.util;


import com.bbek.BbekServiceA.entities.EventEntity;
import com.bbek.BbekServiceA.service.MinistryService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public String savedImage(String root, MultipartFile file){
        try{
            String uploadPathImage = root + "\\" + ((DateTimeFormatter.ofPattern("yyyy-MM")).format(LocalDateTime.now()));
            System.out.println(root+" "+file);
            File directory2= new File(uploadPathImage);
            if(!directory2.exists()){
                directory2.mkdirs();
            }
            String[] ext2 = file.getOriginalFilename().split("\\.");
            String filePath = uploadPathImage+"\\"+new Dates().getCurrentDateTime1()+"-"+generateRandomString()+"."+ext2[ext2.length-1];
            String fileName = new File(filePath).getName();
            saveImage(file, filePath);
            return filePath;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void saveImage(MultipartFile file, String filePath) throws IOException {

        try {
            byte[] data = file.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, data);

        } catch (IOException ex) {
            System.out.println(ex);
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

    public boolean deleteFile(String path){
        String oldPath = path;
        System.out.println("Path: "+oldPath);
        File myobj = new File(oldPath);
        if(myobj.exists()){
            myobj.delete();
            return true;
        }
        return false;
    }
}
