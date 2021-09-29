package com.example.hotels.service.impl;

import com.example.hotels.dto.HotelDTO;
import com.example.hotels.model.Hotel;
import com.example.hotels.service.MappingUtilService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MappingUtilImpl implements MappingUtilService {


    @Override
    public List<HotelDTO> mapToProductDto(List<Hotel> listHotels) {

        List<HotelDTO> hotelDTOS = new ArrayList<>();

        for (Hotel hotel : listHotels){
            HotelDTO hotelDTO = new HotelDTO(hotel.getId(), hotel.getName(), hotel.getStars(),
                    hotel.isWorkingStatus());
            hotelDTOS.add(hotelDTO);
        }
        return hotelDTOS;
    }
}
