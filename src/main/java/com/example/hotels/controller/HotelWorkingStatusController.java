package com.example.hotels.controller;


import com.example.hotels.dto.HotelDTO;
import com.example.hotels.hmac.HMACAuthFilter;
import com.example.hotels.model.Hotel;
import com.example.hotels.service.HotelService;
import com.example.hotels.service.MappingUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelWorkingStatusController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private MappingUtilService mappingUtilService;
    @Autowired
    private HMACAuthFilter hmacAuthFilter;

    @GetMapping
    public ResponseEntity<List<HotelDTO>> hotelsList(@RequestParam(required = false,defaultValue = "0") int page,
                                                  @RequestParam(required = false,defaultValue = "50") int size,
                                                  @RequestParam(required = false,defaultValue = "name") String sort){
        List<Hotel> allHotels = hotelService.findAll(page,size,sort).getContent();
        List<HotelDTO> hotelDTOS = mappingUtilService.mapToProductDto(allHotels);
        return new ResponseEntity<>(hotelDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HotelDTO> setWorkingStatus(@RequestBody HotelDTO hotelDTO,ServletRequest request)
            throws IOException, ServletException {
        if(hmacAuthFilter.doFilter(request)) {
            Hotel hotel = hotelService.findById(hotelDTO.getId());
            if (hotel.getName().equals(hotelDTO.getName())) {
                hotel.setWorkingStatus(hotelDTO.isWorkingStatus());
                hotelService.save(hotel);
                return new ResponseEntity<>(hotelDTO, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(hotelDTO,HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(ServletRequest request) throws ServletException, IOException {
        if(hmacAuthFilter.doFilter(request)){
            return new ResponseEntity<String>("Success!",HttpStatus.OK);
        }
        return new ResponseEntity<String>("Error!",HttpStatus.FORBIDDEN);
    }

}
