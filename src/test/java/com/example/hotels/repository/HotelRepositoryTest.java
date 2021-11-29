package com.example.hotels.repository;

import com.example.hotels.model.Hotel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;
    private static String name = "Hotel";
    Hotel hotel;

    @BeforeEach
    public void init(){
        hotel = new Hotel();
        hotel.setName(name);
        hotel.setCoordinates("123123");
        hotelRepository.save(hotel);
    }
    @AfterEach
    public void after(){
        hotelRepository.delete(hotel);
    }

    @Test
    void findById(){
        assertEquals(hotelRepository.findById(hotel.getId()).getId(),hotel.getId());
    }
    @Test
    void findByName(){
        assertEquals(hotelRepository.findByName(hotel.getName()).getName(),hotel.getName());
    }
    @Test
    void findAll(){
        assertEquals(hotelRepository.findAll().contains(hotel),true);
    }
    @Test
    void findAllByNameStartingWithIgnoreCase(){
        assertEquals(hotelRepository.findAllByNameStartingWithIgnoreCase("hot").contains(hotel),true);
    }
}
