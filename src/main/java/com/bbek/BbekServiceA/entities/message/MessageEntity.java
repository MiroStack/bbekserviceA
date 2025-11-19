package com.bbek.BbekServiceA.entities.message;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "subject")
    private String subject;

    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "status")
    private Long status;

    @Column(name = "created_Dt")
    private LocalDateTime createdDt;

    @Column(name = "created_by_id")
    private Long createdById;


}
