package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.OfferingEntity;
import com.bbek.BbekServiceA.entities.OfferingPaymentRfEntity;
import com.bbek.BbekServiceA.entities.OfferingTypeRfEntity;
import com.bbek.BbekServiceA.entities.modified.offering.ModifiedOfferingEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.OfferingModel;
import com.bbek.BbekServiceA.repository.OfferingPaymentRepo;
import com.bbek.BbekServiceA.repository.OfferingRepo;
import com.bbek.BbekServiceA.repository.OfferingTypeRepo;
import com.bbek.BbekServiceA.service.OfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


import static com.bbek.BbekServiceA.util.Constant.NOT_FOUND;
import static com.bbek.BbekServiceA.util.Constant.SUCCESS;

@Service
public class OfferingServiceImp implements OfferingService {
    @Autowired
    OfferingRepo oRepo;
    @Autowired
    OfferingTypeRepo otRepo;
    @Autowired
    OfferingPaymentRepo opRepo;
    @Override
    public ApiResponseModel getAllOffering(String query, int page) {
        ApiResponseModel res = new ApiResponseModel();
        try{
            String formattedQuery = "%"+query+"%";
            int numberOfRowsToSkip = page == 1? 0 : (page - 1) * 10;
            List<ModifiedOfferingEntity> listOfOfferings = oRepo.getPaginatedOffering(formattedQuery, numberOfRowsToSkip);
            res.setMessage(SUCCESS);
            res.setStatusCode(200);
            res.setData(listOfOfferings);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel submitOffering(OfferingModel m, boolean isUpdate) {
        ApiResponseModel res = new ApiResponseModel();
        try{
            OfferingTypeRfEntity offeringTypeRfEntity = otRepo.findByOfferingType(m.getOfferingType());
            OfferingPaymentRfEntity offeringPaymentRfEntity = opRepo.findByPaymentMethod(m.getPaymentMethod());
            if(offeringPaymentRfEntity == null || offeringTypeRfEntity ==null){
                res.setMessage("Invalid payment method of type.");
                res.setStatusCode(404);
                return res;
            }
            OfferingEntity offeringEntity = new OfferingEntity();
            if(isUpdate){
                offeringEntity.setId(m.getId());
            }
            offeringEntity.setMemberName(m.getMemberName());
            offeringEntity.setAmount(m.getAmount());
            offeringEntity.setOfferingDate(m.getOfferingDate());
            offeringEntity.setOfferingType(offeringTypeRfEntity.getId());
            offeringEntity.setPaymentMethod(offeringPaymentRfEntity.getId());
            offeringEntity.setNotes(m.getNotes());
            oRepo.save(offeringEntity);
            res.setStatusCode(!isUpdate?201:200);
            res.setMessage(!isUpdate?"Donation saved successfully.":"Record updated successfully.");
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel updateOfferingDetails() {
        return null;
    }

    @Override
    public ApiResponseModel getOffering(Long id) {
        return null;
    }

    @Override
    public ApiResponseModel deleteOffering(Long id) {
        ApiResponseModel res = new ApiResponseModel();
        try{
            Optional<OfferingEntity> oOptional = oRepo.findById(id);
            OfferingEntity entity = oOptional.orElse(null);
            if(entity == null){
                res.setStatusCode(404);
                res.setMessage(NOT_FOUND);
                return res;
            }
            oRepo.delete(entity);
            res.setStatusCode(200);
            res.setMessage("Offering record is successfully deleted.");
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ApiResponseModel getAllPaymentTypeRf() {
        ApiResponseModel res = new ApiResponseModel();
        try{
            List<OfferingPaymentRfEntity> paymentTypeList = opRepo.findAll();
            res.setData(paymentTypeList);
            res.setStatusCode(200);
            res.setMessage(SUCCESS);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel getAllOfferingTypeRf() {
        ApiResponseModel res = new ApiResponseModel();
        try {
            List<OfferingTypeRfEntity> offeringTypeList = otRepo.findAll();
            res.setData(offeringTypeList);
            res.setMessage(SUCCESS);
            res.setStatusCode(200);
            return res;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
