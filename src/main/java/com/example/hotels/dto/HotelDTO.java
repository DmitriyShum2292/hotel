package com.example.hotels.dto;

public class HotelDTO {

    private long id;
    private String name;
    private int stars;
    private boolean workingStatus;

    public HotelDTO() {
    }

    public HotelDTO(long id, String name, int stars, boolean workingStatus) {
        this.id = id;
        this.name = name;
        this.stars = stars;
        this.workingStatus = workingStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public boolean isWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(boolean workingStatus) {
        this.workingStatus = workingStatus;
    }
}
