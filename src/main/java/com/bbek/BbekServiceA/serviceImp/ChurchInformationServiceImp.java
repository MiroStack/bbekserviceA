package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.church_information.ChurchInformationEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.repository.information.ChurchInformationRepo;
import com.bbek.BbekServiceA.service.ChurchInformationService;
import com.bbek.BbekServiceA.util.SaveFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.bbek.BbekServiceA.util.Config.getChurchInformationImagePath;
import static com.bbek.BbekServiceA.util.Constant.SUCCESS;

@Service
public class ChurchInformationServiceImp implements ChurchInformationService {
    @Autowired
    ChurchInformationRepo churchInformationRepo;
    @Override
    public ApiResponseModel saveChurchInformation(ChurchInformationEntity entity, MultipartFile file) {
        Optional<ChurchInformationEntity> ceo = churchInformationRepo.findById(entity.getId());
        ChurchInformationEntity ce = ceo.orElse(null);
        String root = getChurchInformationImagePath();
        String filePath = "";
        if(file != null && !file.isEmpty()){
            filePath = new SaveFile().savedImage(root, file);
        }
        if(ce == null){ return new ApiResponseModel("Failed to updated church information .", 404, null);}
            ce = entity;
            if(!filePath.isEmpty()){
                ce.setLogoImagePath(filePath);
            }
            churchInformationRepo.save(ce);
            return new ApiResponseModel("Church information updated successfully.", 200, null);



    }

    @Override
    public ApiResponseModel viewChurchInformation() {
        List<ChurchInformationEntity> information = churchInformationRepo.findAll();
        return new ApiResponseModel(SUCCESS, 200, information.getFirst());
    }

    @Override
    public String viewLogo() {
        List<ChurchInformationEntity> information = churchInformationRepo.findAll();
        return information.getFirst().getLogoImagePath();
    }
}
