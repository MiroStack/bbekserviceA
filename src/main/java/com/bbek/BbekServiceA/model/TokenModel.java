package com.bbek.BbekServiceA.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenModel {
  private String username;
  private Long memberId;
}
