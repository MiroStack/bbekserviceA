package com.bbek.BbekServiceA.model.baptism;

import com.bbek.BbekServiceA.entities.UserProfileEntity;
import com.bbek.BbekServiceA.entities.modified.baptism.ModifiedBaptismEntity;
import com.bbek.BbekServiceA.model.UserInfoModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaptismResponseModel {
    private Long id;
    private UserInfoModel userInfoModel;
    private String testimony;
    private LocalDate preferred_dt;
    private String status;
    private String certificateStatus;
    private String location;
    private String baptismDate;
    private LocalDateTime scheduledDate;
    private String baptismOfficiant;
    private Long baptismOfficiantId;
    private LocalDate createdDate;
    private int totalRows;

}
