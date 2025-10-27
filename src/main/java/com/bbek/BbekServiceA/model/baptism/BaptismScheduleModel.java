package com.bbek.BbekServiceA.model.baptism;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaptismScheduleModel {
    private Long id;
    private Long certificationId;
    private Long baptismStatusId;
    private String baptismDate;
    private String location;
    private Long baptismOfficiantId;
    private String baptismOfficiant;
    private String dateString;
    private String email;
}
