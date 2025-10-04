package com.bbek.BbekServiceA.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAndIdModel {
    private Long id;
    private String fullName;
}
