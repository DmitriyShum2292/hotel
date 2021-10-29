package com.example.hotels.dto;

import java.time.LocalTime;
import java.util.Objects;

public class NotificationDTO {

    private long citizenCardId;
    private LocalTime cleaningTime;
    private String description;

    public NotificationDTO() {
    }

    public NotificationDTO(long citizenCardId, LocalTime cleaningTime, String description) {
        this.citizenCardId = citizenCardId;
        this.cleaningTime = cleaningTime;
        this.description = description;
    }

    public long getCitizenCardId() {
        return citizenCardId;
    }

    public void setCitizenCardId(long citizenCardId) {
        this.citizenCardId = citizenCardId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationDTO that = (NotificationDTO) o;
        return citizenCardId == that.citizenCardId && Objects.equals(cleaningTime, that.cleaningTime) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(citizenCardId, cleaningTime, description);
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "citizenCardId=" + citizenCardId +
                ", cleaningTime=" + cleaningTime +
                ", description='" + description + '\'' +
                '}';
    }
}
