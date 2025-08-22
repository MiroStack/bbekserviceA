package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.OfferingEntity;
import com.bbek.BbekServiceA.entities.OfferingPaymentRfEntity;
import com.bbek.BbekServiceA.entities.OfferingTypeRfEntity;
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
    public ApiResponseModel getAllOffering() {
        ApiResponseModel res = new ApiResponseModel();
        try{
            List<OfferingEntity> listOfOfferings = oRepo.findAll();
            List<OfferingModel>listModel = listOfOfferings.stream().map(m->{
                Optional<OfferingTypeRfEntity> otOptional = otRepo.findById(m.getOfferingType());
                OfferingTypeRfEntity offeringTypeRfEntity = otOptional.orElse(null);
                Optional<OfferingPaymentRfEntity> opOptional = opRepo.findById(m.getPaymentMethod());
                OfferingPaymentRfEntity offeringPaymentRfEntity = opOptional.orElse(null);
                assert offeringTypeRfEntity != null;
                assert offeringPaymentRfEntity != null;
                return new OfferingModel(
                        m.getId(),
                        m.getMemberName(),
                        m.getAmount(),
                        m.getOfferingDate(),
                        offeringTypeRfEntity.getOfferingTypeName(),
                        offeringPaymentRfEntity.getPaymentMethod(),
                        m.getNotes()
                );
            }).toList();
            res.setMessage(SUCCESS);
            res.setStatusCode(200);
            res.setData(listModel);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ApiResponseModel submitOffering(OfferingModel m) {
        ApiResponseModel res = new ApiResponseModel();
        try{
            OfferingTypeRfEntity offeringTypeRfEntity = otRepo.findByOfferingTypeName(m.getOfferingType());
            OfferingPaymentRfEntity offeringPaymentRfEntity = opRepo.findByPaymentMethod(m.getPaymentMethod());
            if(offeringPaymentRfEntity == null || offeringTypeRfEntity ==null){
                res.setMessage("Invalid payment method of type.");
                res.setStatusCode(404);
                return res;
            }
            OfferingEntity offeringEntity = new OfferingEntity();
            offeringEntity.setMemberName(m.getMemberName());
            offeringEntity.setAmount(m.getAmount());
            offeringEntity.setOfferingDate(m.getOfferingDate());
            offeringEntity.setOfferingType(m.getId());
            offeringEntity.setPaymentMethod(offeringPaymentRfEntity.getId());
            offeringEntity.setNotes(m.getNotes());
            oRepo.save(offeringEntity);
            res.setStatusCode(201);
            res.setMessage(SUCCESS);
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
        return null;
    }
}
