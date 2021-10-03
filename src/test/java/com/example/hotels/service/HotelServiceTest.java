package com.example.hotels.service;

import com.example.hotels.model.Hotel;
import com.example.hotels.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class HotelServiceTest {

    @Autowired
    private HotelService hotelService;
    @Mock
    private HotelRepository hotelRepository;

    private Hotel hotel;

    @BeforeEach
    public void init(){
        hotel = new Hotel();
        hotel.setName("Hotel");
    }

    @Test
    void save() {
        //void method
    }

    @Test
    void findById() {
        given(hotelRepository.findById(1)).willReturn(hotel);
        Hotel testHotel = hotelService.findById(1);
        assertThat(testHotel.getName().equals(hotel.getName()));
    }

    @Test
    void findByName() {
        given(hotelRepository.findByName(hotel.getName())).willReturn(hotel);
        Hotel testHotel = hotelRepository.findByName(hotel.getName());
        assertThat(testHotel.getName().equals(hotel.getName()));
    }

    @Test
    void findAllByNameStartingWithIgnoreCase() {
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);
        given(hotelRepository.findAllByNameStartingWithIgnoreCase(hotel.getName())).willReturn(hotels);
        List<Hotel> hotelsTest = hotelService.findAllByNameStartingWithIgnoreCase(hotel.getName());
        assertThat(hotelsTest.size()==0);
    }

    @Test
    void updateWorkingStatus() {
        //empty yet
    }

    @Test
    void delete() {
        //void method
    }
}