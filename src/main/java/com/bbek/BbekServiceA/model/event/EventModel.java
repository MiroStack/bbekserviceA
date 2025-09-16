package com.bbek.BbekServiceA.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventModel {
    private long id;
    private String eventName;
    private String eventType;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eventStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eventEndDate;
    private String eventLocation;
    private int attendance;
    private int offering;
    private String statusName;
    private String description;
}
