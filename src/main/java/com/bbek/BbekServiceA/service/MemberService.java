package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.model.ApiResponseModel;

public interface MemberService {
     ApiResponseModel getMemberPage(String query, int page);
}
