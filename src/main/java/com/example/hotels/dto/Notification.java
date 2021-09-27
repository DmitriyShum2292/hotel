package com.example.hotels.dto;

import java.time.LocalTime;

public class Notification {

    private LocalTime cleaningTime;
    private String description;

    public Notification() {
    }

    public Notification(LocalTime cleaningTime, String description) {
        this.cleaningTime = cleaningTime;
        this.description = description;
    }

    public LocalTime getCleaningTime() {
        return cleaningTime;
    }

    public void setCleaningTime(LocalTime cleaningTime) {
        this.cleaningTime = cleaningTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
