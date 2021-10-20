package com.example.hotels.controller;


import com.example.hotels.dto.CompleteRequestDTO;
import com.example.hotels.model.Order;
import com.example.hotels.model.User;
import com.example.hotels.service.OrderService;
import com.example.hotels.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentResponseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @PostMapping
    public String paymentResponse(@RequestBody CompleteRequestDTO completeRequestDTO){
        User user = userService.findCurrentUser();
        List<Order> orders = user.getOrders();
        for(Order order : orders){
            if(order.getTotalPrice()==completeRequestDTO.getAmount()){
                order.setPaid(true);
                orderService.save(order);
                return "success!";
            }
        }
        return  "Error!";
    }
}
