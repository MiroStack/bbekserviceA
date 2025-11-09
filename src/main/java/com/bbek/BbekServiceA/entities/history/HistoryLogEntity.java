package com.bbek.BbekServiceA.entities.history;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "history_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "status")
    private String status;

    @Column(name="name")
    private String name;

    @Column(name = "created_dt")
    private LocalDateTime createdDt;

    @Column(name = "created_by_id")
    private Long createdById;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "description")
    private String description;
}
