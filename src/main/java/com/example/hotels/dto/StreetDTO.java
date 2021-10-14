package com.example.hotels.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Objects;

public class StreetDTO {

    private long id;
    private String name;
    private DistrictDTO district;

    public StreetDTO() {
    }

    public StreetDTO(long id, String name, DistrictDTO district) {
        this.id = id;
        this.name = name;
        this.district = district;
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

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreetDTO streetDTO = (StreetDTO) o;
        return id == streetDTO.id && Objects.equals(name, streetDTO.name) && Objects.equals(district, streetDTO.district);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, district);
    }

    @Override
    public String toString() {
        return "StreetDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", district=" + district +
                '}';
    }
}
