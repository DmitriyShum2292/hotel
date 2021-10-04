package com.example.hotels.controller;

import com.example.hotels.model.Hotel;
import com.example.hotels.model.User;
import com.example.hotels.service.HotelService;
import com.example.hotels.service.OrderService;
import com.example.hotels.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private OrderService orderService;

    private final  String hotelName = "hotel";
    private final String redirectAdmin = "redirect:/admin";

    @GetMapping
    public String adminPage(Model model){
        User user = userService.findCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute(hotelName, user.getHotel());
        return "admin_cabinet";
    }
    @GetMapping("/hotel")
    public String createHotelPage(Model model){
        User user = userService.findCurrentUser();
        Hotel hotel = new Hotel();
        model.addAttribute("user", user);
        model.addAttribute(hotelName, hotel);
        return "create_hotel";
    }
    @PostMapping("/hotel")
    public String createHotel(@ModelAttribute("hotel") Hotel hotel){
        User user = userService.findCurrentUser();
        hotelService.save(hotel);
        user.setHotel(hotel);
        userService.save(user);
        return redirectAdmin;
    }
    @GetMapping("/hotel/update")
    public String updateHotelPage(Model model){
        User user = userService.findCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute(hotelName, user.getHotel());
        return "update_hotel";
    }
    @PostMapping("/hotel/update")
    public String updateHotel(@ModelAttribute("hotel")Hotel hotel){
        hotelService.save(hotel);
        return redirectAdmin;
    }
    @DeleteMapping("/hotel/{id}")
    public String deleteHotel(@PathVariable long id){
        hotelService.delete(id);
        return redirectAdmin;
    }
}
