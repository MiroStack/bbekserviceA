package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.entities.BaptismEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.RegistrationModel;
import com.bbek.BbekServiceA.model.RegistrationRequestModel;
import com.bbek.BbekServiceA.model.UserInfoModel;
import com.bbek.BbekServiceA.model.baptism.AddBaptismRequestModel;
import com.bbek.BbekServiceA.model.baptism.BaptismResponseModel;
import com.bbek.BbekServiceA.model.baptism.BaptismScheduleModel;

public interface BaptismService {
    ApiResponseModel submitBaptismRequest(RegistrationRequestModel rModel);
    ApiResponseModel getPaginatedBaptism(String query, int page);
    ApiResponseModel getBaptismOfficiants();
    ApiResponseModel getCertificateStatuses();
    ApiResponseModel getBaptismStatuses();
    ApiResponseModel sentBaptismSchedule(BaptismScheduleModel model);
    ApiResponseModel addBaptism(AddBaptismRequestModel model);
}
