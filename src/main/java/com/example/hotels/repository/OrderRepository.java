package com.example.hotels.repository;

import com.example.hotels.model.Hotel;
import com.example.hotels.model.Order;
import com.example.hotels.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findById(long id);
    List<Order>findAllByHotelAndUser(Hotel hotel,User user);
    List<Order> findByHotel(Hotel hotel);
}
