package com.bbek.BbekServiceA.model;

import com.bbek.BbekServiceA.entities.BaptismEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationModel {
    RegistrationRequestModel registrationRequestModel;
    BaptismEntity baptismEntity;
}
