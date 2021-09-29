package com.example.hotels.controller;


import com.example.hotels.dto.HotelDTO;
import com.example.hotels.model.Hotel;
import com.example.hotels.service.HotelService;
import com.example.hotels.service.MappingUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelWorkingStatusController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private MappingUtilService mappingUtilService;

    @GetMapping
    public ResponseEntity<List<HotelDTO>> hotelsList(@RequestParam(required = false,defaultValue = "0") int page,
                                                  @RequestParam(required = false,defaultValue = "50") int size,
                                                  @RequestParam(required = false,defaultValue = "name") String sort){
        List<Hotel> allHotels = hotelService.findAll(page,size,sort).getContent();
        List<HotelDTO> hotelDTOS = mappingUtilService.mapToProductDto(allHotels);
        return new ResponseEntity<>(hotelDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HotelDTO> setWorkingStatus(@RequestBody HotelDTO hotelDTO){
        Hotel hotel = hotelService.findById(hotelDTO.getId());
        if (hotel.getName().equals(hotelDTO.getName())){
            hotel.setWorkingStatus(hotelDTO.isWorkingStatus());
            hotelService.save(hotel);
            return new ResponseEntity<>(hotelDTO,HttpStatus.OK);
        }
        return new ResponseEntity<>(hotelDTO,HttpStatus.BAD_REQUEST);
    }

}
