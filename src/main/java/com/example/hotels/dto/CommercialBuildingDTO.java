package com.example.hotels.dto;

import java.util.Objects;

public class CommercialBuildingDTO {

    private long id;
    private StreetDTO streetDTO;
    private long buildingNumber;
    private String buildingType;

    public CommercialBuildingDTO() {
    }

    public CommercialBuildingDTO(long id, StreetDTO streetDTO, long buildingNumber, String buildingType) {
        this.id = id;
        this.streetDTO = streetDTO;
        this.buildingNumber = buildingNumber;
        this.buildingType = buildingType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StreetDTO getStreetDTO() {
        return streetDTO;
    }

    public void setStreetDTO(StreetDTO streetDTO) {
        this.streetDTO = streetDTO;
    }

    public long getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(long buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommercialBuildingDTO that = (CommercialBuildingDTO) o;
        return id == that.id && buildingNumber == that.buildingNumber && Objects.equals(streetDTO, that.streetDTO) && Objects.equals(buildingType, that.buildingType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, streetDTO, buildingNumber, buildingType);
    }

    @Override
    public String toString() {
        return "CommercialBuildingDTO{" +
                "id=" + id +
                ", streetDTO=" + streetDTO +
                ", buildingNumber=" + buildingNumber +
                ", buildingType='" + buildingType + '\'' +
                '}';
    }
}
