package com.example.hotels.repository;

import com.example.hotels.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {

    Hotel findById(long id);
    Hotel findByName(String name);
    List<Hotel> findAll();
    List<Hotel> findAllByNameStartingWithIgnoreCase(String name);
}
