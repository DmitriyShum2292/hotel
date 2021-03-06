package com.example.hotels.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@DynamicUpdate
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotEmpty(message = "Name can't be empty")
    @Size(min = 2,max = 20,message = "Name should be 2 - 20 characters")
    private String name;
    private int stars;
    private int availableRooms;
    private boolean workingStatus;
    private long dailyCost;
    private LocalTime cleaningTime;
    @NotEmpty(message = "Coordinates can't be empty")
    private String coordinates;
    private String hotelImageLink;
    private String roomImageLink;
    private String description;
    private long homeId;

    public Hotel() {
    }

    public Hotel(String name, int stars, int availableRooms, boolean workingStatus,
                 long dailyCost, LocalTime cleaningTime, long homeId) {
        this.name = name;
        this.stars = stars;
        this.availableRooms = availableRooms;
        this.workingStatus = workingStatus;
        this.dailyCost = dailyCost;
        this.cleaningTime = cleaningTime;
        this.homeId = homeId;
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

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    public boolean isWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(boolean workingStatus) {
        this.workingStatus = workingStatus;
    }

    public long getDailyCost() {
        return dailyCost;
    }

    public void setDailyCost(long dailyCost) {
        this.dailyCost = dailyCost;
    }

    public LocalTime getCleaningTime() {
        return cleaningTime;
    }

    public void setCleaningTime(LocalTime cleaningTime) {
        this.cleaningTime = cleaningTime;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getHotelImageLink() {
        return hotelImageLink;
    }

    public void setHotelImageLink(String hotelImageLink) {
        this.hotelImageLink = hotelImageLink;
    }

    public String getRoomImageLink() {
        return roomImageLink;
    }

    public void setRoomImageLink(String roomImageLink) {
        this.roomImageLink = roomImageLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getHomeId() {
        return homeId;
    }

    public void setHomeId(long homeId) {
        this.homeId = homeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return id == hotel.id && stars == hotel.stars && availableRooms == hotel.availableRooms && workingStatus == hotel.workingStatus && Double.compare(hotel.dailyCost, dailyCost) == 0 && homeId == hotel.homeId && Objects.equals(name, hotel.name) && Objects.equals(cleaningTime, hotel.cleaningTime) && Objects.equals(coordinates, hotel.coordinates) && Objects.equals(hotelImageLink, hotel.hotelImageLink) && Objects.equals(roomImageLink, hotel.roomImageLink) && Objects.equals(description, hotel.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, stars, availableRooms, workingStatus, dailyCost, cleaningTime, coordinates, hotelImageLink, roomImageLink, description, homeId);
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stars=" + stars +
                ", availableRooms=" + availableRooms +
                ", workingStatus=" + workingStatus +
                ", dailyCost=" + dailyCost +
                ", cleaningTime=" + cleaningTime +
                ", coordinates='" + coordinates + '\'' +
                ", hotelImageLink='" + hotelImageLink + '\'' +
                ", roomImageLink='" + roomImageLink + '\'' +
                ", description='" + description + '\'' +
                ", homeId=" + homeId +
                '}';
    }
}
