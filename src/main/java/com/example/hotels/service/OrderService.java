package com.example.hotels.service;

import com.example.hotels.dto.CompleteRequestDTO;
import com.example.hotels.model.Hotel;
import com.example.hotels.model.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface OrderService {

    void save(Order order);
    Order findById(long id);
    String pay(Order order) throws IOException;
    boolean sendNotification(Hotel hotel) throws IOException;
    void delete(long id);
    boolean paymentResponse(CompleteRequestDTO completeRequestDTO);

}
