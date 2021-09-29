package com.example.hotels.service;

import com.example.hotels.dto.HotelDTO;
import com.example.hotels.model.Hotel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MappingUtilService {

    List<HotelDTO> mapToProductDto(List<Hotel> hotels);
}
