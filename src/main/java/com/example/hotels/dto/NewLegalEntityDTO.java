package com.example.hotels.dto;

import java.util.Objects;

public class NewLegalEntityDTO {
    private String name;
    private long homeId;
    private long ownerCardNumber;

    public NewLegalEntityDTO() {
    }

    public NewLegalEntityDTO(String name, long homeId, long ownerCardNumber) {
        this.name = name;
        this.homeId = homeId;
        this.ownerCardNumber = ownerCardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getHomeId() {
        return homeId;
    }

    public void setHomeId(long homeId) {
        this.homeId = homeId;
    }

    public long getOwnerCardNumber() {
        return ownerCardNumber;
    }

    public void setOwnerCardNumber(long ownerCardNumber) {
        this.ownerCardNumber = ownerCardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewLegalEntityDTO that = (NewLegalEntityDTO) o;
        return homeId == that.homeId && Objects.equals(name, that.name) && Objects.equals(ownerCardNumber, that.ownerCardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, homeId, ownerCardNumber);
    }

    @Override
    public String toString() {
        return "NewLegalEntityDTO{" +
                "name='" + name + '\'' +
                ", homeId=" + homeId +
                ", ownerCardNumber='" + ownerCardNumber + '\'' +
                '}';
    }
}
