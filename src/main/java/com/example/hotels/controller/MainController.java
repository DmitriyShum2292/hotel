package com.example.hotels.controller;

import com.example.hotels.model.User;
import com.example.hotels.service.ExternalApiService;
import com.example.hotels.service.HotelService;
import com.example.hotels.service.OrderService;
import com.example.hotels.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for main page
 */
@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private ExternalApiService externalApiService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private OrderService orderService;

    /**
     * @param model
     * return main page
     */
    @GetMapping
    public String mainPage(Model model) {
        User user;
        try {
            user = userService.findCurrentUser();
        }
        catch (ClassCastException e){
            return "main";
        }
        model.addAttribute("user", user);
        return "main";
    }
}
