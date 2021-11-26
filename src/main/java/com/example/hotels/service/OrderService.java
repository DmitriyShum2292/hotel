package com.example.hotels.service;

import com.example.hotels.dto.CompleteRequestDTO;
import com.example.hotels.model.Hotel;
import com.example.hotels.model.Order;
import com.example.hotels.model.User;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;

@Component
public interface OrderService {

    void save(Order order);
    Order findById(long id);
    List<Order>findAllByHotelAndUser(Hotel hotel, User user);
    List<Order>findByHotel(Hotel hotel);
    String pay(Order order) throws IOException;
    boolean sendNotification(Hotel hotel) throws IOException;
    void delete(long id);
    void setPaid(CompleteRequestDTO completeRequestDTO) throws IOException;

}
