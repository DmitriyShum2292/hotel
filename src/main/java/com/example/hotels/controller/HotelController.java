package com.example.hotels.controller;


import com.example.hotels.model.Hotel;
import com.example.hotels.model.User;
import com.example.hotels.service.HotelService;
import com.example.hotels.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public String HotelPage(@PathVariable long id, Model model){
        Hotel hotel = hotelService.findById(id);
        User user = userService.findCurrentUser();
        model.addAttribute("hotel", hotel);
        model.addAttribute("user", user);
        return "hotel";
    }


}
