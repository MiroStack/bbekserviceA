package com.bbek.BbekServiceA.model;

import lombok.Data;

@Data
public class ApiResponseModel {
   private String message;
   private int statusCode;
   private Object data;
}
