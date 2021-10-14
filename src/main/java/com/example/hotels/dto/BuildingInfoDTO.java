package com.example.hotels.dto;

import java.util.Objects;

public class BuildingInfoDTO {
    private long id;
    private long homeCode;
    private long homeNumber;
    private CommercialBuildingDTO building;
    private boolean inUse;

    public BuildingInfoDTO() {
    }

    public BuildingInfoDTO(long id, long homeCode, long homeNumber, CommercialBuildingDTO building, boolean inUse) {
        this.id = id;
        this.homeCode = homeCode;
        this.homeNumber = homeNumber;
        this.building = building;
        this.inUse = inUse;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHomeCode() {
        return homeCode;
    }

    public void setHomeCode(long homeCode) {
        this.homeCode = homeCode;
    }

    public long getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(long homeNumber) {
        this.homeNumber = homeNumber;
    }

    public CommercialBuildingDTO getBuilding() {
        return building;
    }

    public void setBuilding(CommercialBuildingDTO building) {
        this.building = building;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuildingInfoDTO that = (BuildingInfoDTO) o;
        return id == that.id && homeCode == that.homeCode && homeNumber == that.homeNumber && inUse == that.inUse && Objects.equals(building, that.building);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, homeCode, homeNumber, building, inUse);
    }

    @Override
    public String toString() {
        return "BuildingInfoDTO{" +
                "id=" + id +
                ", homeCode=" + homeCode +
                ", homeNumber=" + homeNumber +
                ", building=" + building +
                ", inUse=" + inUse +
                '}';
    }
}
