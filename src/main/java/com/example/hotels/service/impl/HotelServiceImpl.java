package com.example.hotels.service.impl;

import com.example.hotels.model.Hotel;
import com.example.hotels.model.User;
import com.example.hotels.repository.HotelRepository;
import com.example.hotels.service.HotelService;
import com.example.hotels.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private UserService userService;

    @Override
    public void save(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    @Override
    public Hotel findById(long id) {
        return hotelRepository.findById(id);
    }

    @Override
    public Hotel findByName(String name) {
        return hotelRepository.findByName(name);
    }

    @Override
    public List<Hotel> findAllByNameStartingWithIgnoreCase(String name) {
        return hotelRepository.findAllByNameStartingWithIgnoreCase(name);
    }

    @Override
    public Page<Hotel> findAll(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort));
        return hotelRepository.findAll(pageable);
    }


    @Override
    public void delete(long id) {
        User user = userService.findCurrentUser();
        Hotel hotel = findById(id);
        user.setHotel(null);
        userService.save(user);
        hotelRepository.delete(hotel);
    }
}
