package com.example.hotels.controller;

import com.example.hotels.dto.ApiResponse;
import com.example.hotels.dto.CompleteRequestDTO;
import com.example.hotels.service.OrderService;
import com.example.hotels.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentResponseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    /**
     * receive response after payment is done in citizen account module
     */
    @PostMapping
    public ResponseEntity<ApiResponse> setPaid(@RequestBody CompleteRequestDTO completeRequestDTO) throws IOException {
        orderService.setPaid(completeRequestDTO);

        return new ResponseEntity<>(new ApiResponse(true,"true",completeRequestDTO),HttpStatus.OK);
    }
}
