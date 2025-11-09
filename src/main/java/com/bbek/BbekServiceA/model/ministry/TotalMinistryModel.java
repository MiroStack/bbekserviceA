package com.bbek.BbekServiceA.model.ministry;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TotalMinistryModel {
    private Long totalMinistries;
    private Long totalActiveMinistries;
    private Long totalMembers;
}
