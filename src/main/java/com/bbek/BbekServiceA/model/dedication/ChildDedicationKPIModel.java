package com.bbek.BbekServiceA.model.dedication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildDedicationKPIModel {
    private Long totaDedication;
    private Long completedReqDedication;
    private Long newReqDedication;
}
