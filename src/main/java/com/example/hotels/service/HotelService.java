package com.example.hotels.service;

import com.example.hotels.dto.DistrictDTO;
import com.example.hotels.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface HotelService {
    void save(Hotel hotel);
    Hotel findById(long id);
    Hotel findByName(String name);
    List<Hotel> findAllByNameStartingWithIgnoreCase(String name);
    Page<Hotel> findAll(int page, int size,String sort);
    void delete(long id);
    List<DistrictDTO> getAllDistricts() throws IOException;
}
