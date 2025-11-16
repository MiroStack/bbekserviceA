package com.bbek.BbekServiceA.model.ministry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMinistryModel {
  private long id;
  private String ministryName;
  private String scheduleDay;
  private String startTime;
  private String endTime;
  private String ministryLeader;
  private MultipartFile ministryImage;
  private String department;
  private String status;
  private String description;
  private boolean update;
}
