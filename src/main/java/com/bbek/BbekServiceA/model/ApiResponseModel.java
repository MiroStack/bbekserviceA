package com.bbek.BbekServiceA.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseModel {
   private String message;
   private int statusCode;
   private Object data;
}
