package com.bbek.BbekServiceA.model.dedication;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildDedicationReqModel {
    private Long reqId;
    private Long memberId;
    private String childName;
    private String guardianName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dedicationDt;
    private String contactNo;
    private Long statusId;
}
