package com.example.hotels.service;

import com.example.hotels.model.Order;
import com.example.hotels.model.User;
import com.example.hotels.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @MockBean
    private OrderRepository orderRepository;

    private User user;
    private Order order;

    @BeforeEach
    public void init(){
        user = new User();
        user.setNickName("Login");
        order = new Order();
        order.setId(1);
        order.setPeriod(1);
        order.setTotalPrice(2);
        order.setPaid(true);
        user.setOrders(Arrays.asList(order));
    }

    @Test
    void save() {
        //void method
    }

    @Test
    void findById() {
        given(orderRepository.findById(1)).willReturn(order);
        Order orderTest = orderService.findById(1);
        assertThat(orderTest.getId()==1);
    }


    @Test
    void pay() {
        //http to external api
    }

    @Test
    void sendNotification() {
        //http to external api
    }

    @Test
    void delete() {
        //void method
    }
}