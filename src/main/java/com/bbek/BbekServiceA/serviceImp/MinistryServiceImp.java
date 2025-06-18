package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.MinistryEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.repository.MinistryRepo;
import com.bbek.BbekServiceA.service.MinistryService;
import com.bbek.BbekServiceA.util.Config;
import com.bbek.BbekServiceA.util.Dates;
import com.bbek.BbekServiceA.util.SaveFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.bbek.BbekServiceA.util.Constant.FAILED;
import static com.bbek.BbekServiceA.util.Constant.SUCCESS;

@Service
public class MinistryServiceImp implements MinistryService {
    @Autowired
    MinistryRepo mRepo;

    @Override
    public List<MinistryEntity> getAllMinistryList() {

        return mRepo.findAll();
    }

    @Override
    public ApiResponseModel saveMinistry(MinistryEntity entity, MultipartFile file) {
        ApiResponseModel res = new ApiResponseModel();
        try{
            String ministryPath = Config.getMinistryImagePath();
            String fileUploadPathImage = ministryPath + "\\" + ((DateTimeFormatter.ofPattern("yyyy-MM")).format(LocalDateTime.now()));
            File ROOT_BASE_PATH = new File(fileUploadPathImage);
            if(!ROOT_BASE_PATH.exists()){
                ROOT_BASE_PATH.mkdirs();
            }
            String[] ext = file.getOriginalFilename().split("\\.");
            String filePath = fileUploadPathImage+"\\"+new Dates().getCurrentDateTime1()+"-"+new SaveFile().generateRandomString()+"."+ext[ext.length-1];
            MinistryEntity entity1 = entity;
            entity1.setFilepath(filePath);
            mRepo.save(entity);
            new SaveFile().saveFile(file, filePath);

            res.setMessage(SUCCESS);
            res.setStatusCode(201);
            return res;

        } catch (Exception e) {
            mRepo.save(entity);
            res.setMessage(FAILED);
            res.setStatusCode(201);
            e.printStackTrace();
            return res;

        }

    }
}
