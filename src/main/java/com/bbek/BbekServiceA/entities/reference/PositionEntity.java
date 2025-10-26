package com.bbek.BbekServiceA.entities.reference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "rf_position")
@AllArgsConstructor
@NoArgsConstructor
public class PositionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name="position_name")
    private String positionName;
}
