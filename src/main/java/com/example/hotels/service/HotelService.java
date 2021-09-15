package com.example.hotels.service;

import com.example.hotels.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface HotelService {
    void save(Hotel hotel);
    Hotel findById(long id);
    Hotel findByName(String name);
    List<Hotel> findAllByNameStartingWithIgnoreCase(String name);
    Page<Hotel> findAll(int page, int size,String sort);
    void updateWorkingStatus(boolean status);
    void delete(long id);
}
