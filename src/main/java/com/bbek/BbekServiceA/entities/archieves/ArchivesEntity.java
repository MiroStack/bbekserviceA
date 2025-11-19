package com.bbek.BbekServiceA.entities.archieves;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "archieves")
public class ArchivesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "filepath")
    private String filePath;

    @Column(name = "description")
    private String description;

    @Column(name = "date_archives")
    private Long dateArchives;

    @Column(name = "status")
    private Long status;

    @Column(name = "file_size")
    private String fileSize;

    @Column(name = "created_by_id")
    private Long createdByID;

    @Column(name = "created_dt")
    private LocalDateTime createdDt;
}
