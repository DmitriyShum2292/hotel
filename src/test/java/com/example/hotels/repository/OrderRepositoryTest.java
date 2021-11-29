package com.example.hotels.repository;


import com.example.hotels.model.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    Order order;

    @BeforeEach
    public void init(){
        order = new Order();
        orderRepository.save(order);
    }
    @AfterEach
    public void after(){
        orderRepository.delete(order);
    }
    @Test
     void findById(){
        assertEquals(orderRepository.findById(order.getId()).getId(),order.getId());
    }
}
