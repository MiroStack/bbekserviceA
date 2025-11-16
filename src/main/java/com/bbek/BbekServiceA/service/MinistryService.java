package com.bbek.BbekServiceA.service;

import com.bbek.BbekServiceA.entities.ministries.MinistryEntity;
import com.bbek.BbekServiceA.entities.pivot.MinistryPivotEntity;
import com.bbek.BbekServiceA.model.ApiResponseModel;
import com.bbek.BbekServiceA.model.ministry.CreateMinistryModel;
import com.bbek.BbekServiceA.model.ministry.MinistryModel;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface MinistryService {
    public List<MinistryModel> getAllMinistryList(String query, int page);
    public ApiResponseModel saveMinistry(CreateMinistryModel model);
    public String getMinistryImage(String ministryName);
    ApiResponseModel getMinistry(Long id);
    ApiResponseModel deleteMinistry(Long id);
    ApiResponseModel getUpcomingMinistry();
    ApiResponseModel getPaginatedMinistry(String query, int page);
    ApiResponseModel getMinistryStatuses();
    ApiResponseModel joinMinistry(MinistryPivotEntity entity);
    ApiResponseModel leaveMinistry(Long id);
    List<MinistryModel> getAllLadiesMinistries(String query, int page);
    List<MinistryModel> getAllMenMinistries(String query, int page);
    List<MinistryModel> getYoungPeopleMinistries(String query, int page);
    List<MinistryModel> getMyMinistry(String query, int page, Long userId);
    ApiResponseModel updateMemberJoinApplication(Long pivotId, String statusName, Long userId);
    ApiResponseModel viewMembersOfMinistries(Long ministryId, String query, int page);
    ApiResponseModel viewTotalMembersPerMinistry(Long ministryId);
    ApiResponseModel getTotalMinistryAndMembers();
}
