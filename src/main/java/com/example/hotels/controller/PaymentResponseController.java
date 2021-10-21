package com.example.hotels.controller;

import com.example.hotels.dto.CompleteRequestDTO;
import com.example.hotels.service.OrderService;
import com.example.hotels.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentResponseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String test(){
        return "Hello!";
    }

    /**
     * receive response after payment is done in citizen account module
     */
    @PostMapping
    public ResponseEntity<?> paymentResponse(@RequestBody CompleteRequestDTO completeRequestDTO){
        if (orderService.paymentResponse(completeRequestDTO)){
            return new ResponseEntity<>("Success!",HttpStatus.OK);
        }
        return  new ResponseEntity<>("Order doesn't exist!",HttpStatus.BAD_REQUEST);
    }
}
