package com.bbek.BbekServiceA.model.information;

import com.bbek.BbekServiceA.entities.church_information.ChurchInformationEntity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChurchInformationReqModel {
    private String churchName;
    private String acronym;
    private String email;
    private String address;
    private String phone;
    private String website;
    private String mission;
    private String vision;
    private MultipartFile file;
}
