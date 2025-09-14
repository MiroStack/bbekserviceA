package com.bbek.BbekServiceA.model;

import com.bbek.BbekServiceA.entities.MarriageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarriageResponseModel {
    private MarriageEntity entity;
    private boolean update;
}
