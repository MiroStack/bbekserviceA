package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.MarriageEntity;
import com.bbek.BbekServiceA.entities.MarriageLocationRfEntity;
import com.bbek.BbekServiceA.entities.MarriageStatusRfEntity;
import com.bbek.BbekServiceA.entities.modified.marriage.ModifiedMarriageEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.repository.MarriageLocationRepo;
import com.bbek.BbekServiceA.repository.MarriageRepo;
import com.bbek.BbekServiceA.repository.MarriageStatusRepo;
import com.bbek.BbekServiceA.service.MarriageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.bbek.BbekServiceA.util.Constant.NOT_FOUND;
import static com.bbek.BbekServiceA.util.Constant.SUCCESS;

@Service
public class MarriageServiceImp implements MarriageService {
    @Autowired
    MarriageRepo mRepo;

    @Autowired
    MarriageStatusRepo msRepo;

    @Autowired
    MarriageLocationRepo mlRepo;
    @Override
    public ApiResponseModel getAllMarriages(String query, int page) {
        ApiResponseModel res = new ApiResponseModel();
        try{
            String formattedQuery = "%"+query+"%";
            int numberOfRowsToSkip = page == 1? 0 : (page - 1) * 10;
            List<ModifiedMarriageEntity> list = mRepo.getPaginatedMarriage(formattedQuery, numberOfRowsToSkip);
            res.setData(list);
            res.setStatusCode(200);
            res.setMessage(SUCCESS);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel saveMarriages(MarriageEntity entity, boolean isUpdate) {
        ApiResponseModel res = new ApiResponseModel();
      try{
          if(isUpdate){
              Optional<MarriageEntity> marriageEntityOptional = mRepo.findById(entity.getId());
              MarriageEntity marriageEntity = marriageEntityOptional.orElse(null);
              if(marriageEntity == null){
                res.setMessage(NOT_FOUND);
                res.setStatusCode(404);
                return res;
              }
              marriageEntity = entity;
              mRepo.save(marriageEntity);
              res.setStatusCode(200);
              res.setMessage("Marriage is successfully updated.");
              return res;
          }else{
              entity.setCreatedDate(LocalDateTime.now());
              entity.setId(null);
              mRepo.save(entity);
              res.setStatusCode(201);
              res.setMessage("Marriage is successfully saved.");
              return res;
          }
        } catch (Exception e) {
          throw new RuntimeException(e);
      }
    }

    @Override
    public ApiResponseModel deleteMarriage(Long id) {
        ApiResponseModel res = new ApiResponseModel();
        try{
            Optional<MarriageEntity> marriageEntityOptional = mRepo.findById(id);
            MarriageEntity marriageEntity = marriageEntityOptional.orElse(null);
            if(marriageEntity == null){
                res.setMessage(NOT_FOUND);
                res.setStatusCode(404);
                return res;
            }
            mRepo.delete(marriageEntity);
            res.setStatusCode(200);
            res.setMessage("Marriage record deleted successfully.");
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel getAllMarriageStatuses() {
        ApiResponseModel res = new ApiResponseModel();
        try{
            List<MarriageStatusRfEntity> marriageStatusRfEntities = msRepo.findAll();
            res.setMessage(SUCCESS);
            res.setData(marriageStatusRfEntities);
            res.setStatusCode(200);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel getAllLocations() {
        ApiResponseModel res = new ApiResponseModel();
        try{
            List<MarriageLocationRfEntity> marriageLocationRfEntities = mlRepo.findAll();
            res.setMessage(SUCCESS);
            res.setData(marriageLocationRfEntities);
            res.setStatusCode(200);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
