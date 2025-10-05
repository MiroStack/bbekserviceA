package com.bbek.BbekServiceA.serviceImp;

import com.bbek.BbekServiceA.entities.UserProfileEntity;
import com.bbek.BbekServiceA.entities.modified.member.ModifiedMemberEntity;
import com.bbek.BbekServiceA.entities.reference.StatusEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.member.MemberResponseModel;
import com.bbek.BbekServiceA.repository.MemberRepo;
import com.bbek.BbekServiceA.repository.UserProfileRepo;
import com.bbek.BbekServiceA.repository.reference.StatusRepo;
import com.bbek.BbekServiceA.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.bbek.BbekServiceA.util.Constant.SUCCESS;

@Service
public class MemberServiceImp implements MemberService {
    @Autowired
    MemberRepo memberRepo;
    @Autowired
    UserProfileRepo upRepo;
    @Autowired
    StatusRepo statusRepo;

    @Override
    public ApiResponseModel getMemberPage(String query, int page) {
        ApiResponseModel res = new ApiResponseModel();
        try {
            String queryFormatted = "%" + query + "%";
            int numberOfRowsToSkip = page == 1 ? 0 : (page - 1) * 10;
            List<ModifiedMemberEntity> list = memberRepo.getMembers(queryFormatted, numberOfRowsToSkip);
            List<MemberResponseModel> data = list.stream().map(m -> {

                Optional<UserProfileEntity> upo = upRepo.findById(m.getProfileId());
                UserProfileEntity upe = upo.orElse(null);
                Optional<StatusEntity> sto = statusRepo.findById(m.getStatusId());
                StatusEntity ste = sto.orElse(null);
                MemberResponseModel mrm = new MemberResponseModel(
                        m.getId(),
                        m.getMemberName(),
                        upe.getAge(),
                        upe.getAddress(),
                        ste.getStatusName(),
                        m.getJoinDate(),
                        m.isActive(),
                        m.getTotalRows());


                return mrm;
            }).toList();
            res.setMessage(SUCCESS);
            res.setData(data);
            res.setStatusCode(200);
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
