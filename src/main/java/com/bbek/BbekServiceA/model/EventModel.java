package com.bbek.BbekServiceA.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventModel {
    private String eventName;
    private String eventType;
    private String eventDate;
    private String eventTime;
    private String eventLocation;
    private int attendance;
    private int offering;
    private String statusName;

}
