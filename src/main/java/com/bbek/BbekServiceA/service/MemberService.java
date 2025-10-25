package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.baptism.AddBaptismRequestModel;

public interface MemberService {
     ApiResponseModel getMemberPage(String query, int page);
     ApiResponseModel addMember(AddBaptismRequestModel model);
     ApiResponseModel viewDetails(Long memberId);
}
