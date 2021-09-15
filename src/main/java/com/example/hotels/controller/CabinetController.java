package com.example.hotels.controller;

import com.example.hotels.model.Hotel;
import com.example.hotels.model.Order;
import com.example.hotels.model.User;
import com.example.hotels.service.HotelService;
import com.example.hotels.service.OrderService;
import com.example.hotels.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;


@Controller
@RequestMapping("/cabinet")
public class CabinetController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String cabinet(@RequestParam(required = false,defaultValue = "0") int page,
                          @RequestParam(required = false,defaultValue = "10") int size,
                          @RequestParam(required = false,defaultValue = "0") String name,
                          @RequestParam(required = false, defaultValue = "name") String sort, Model model){
        User user = userService.findCurrentUser();
        if(!name.equals("0")) {
            model.addAttribute("hotels", hotelService.findAllByNameStartingWithIgnoreCase(name));
        }
        else {
            Page<Hotel> all = hotelService.findAll(page,size,sort);
            if (all.isEmpty()){throw new NullPointerException();}
            model.addAttribute("hotels", hotelService.findAll(page, size, sort));
        }
        model.addAttribute("user",user);
        model.addAttribute("orders",userService.findOrdersByUser());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        return "cabinet";
    }
    @GetMapping("/{page}")
    public String cabinetPagination(@PathVariable int page,
                          @RequestParam(required = false,defaultValue = "10") int size,
                          @RequestParam(required = false, defaultValue = "name") String sort, Model model){
        User user = userService.findCurrentUser();
        Page<Hotel> all = hotelService.findAll(page,size,sort);
        if (all.isEmpty()){throw new NullPointerException();}
        model.addAttribute("user",user);
        model.addAttribute("hotels",all);
        model.addAttribute("orders",userService.findOrdersByUser());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        return "cabinet";
    }

    @GetMapping("/order/{hotel}")
    public String createOrderPage(@PathVariable String hotel , Model model){
        User user = userService.findCurrentUser();
        model.addAttribute("user",user);
        Hotel foundHotel = hotelService.findByName(hotel);
        if (foundHotel == null){throw new NullPointerException();}
        model.addAttribute("hotel",foundHotel);
        return "order";
    }

    @PostMapping("/order")
    public String createOrder(@RequestParam String hotelName ,@RequestParam int period ,
                              @RequestParam String booking){
        Hotel hotel = hotelService.findByName(hotelName);
        LocalDateTime dateTime = LocalDateTime.parse(booking);
        Order order = new Order(hotel, dateTime,period);
        orderService.save(order);
        return "redirect:/cabinet";
    }

    @DeleteMapping("/order/{id}")
    public String deleteOrder(@PathVariable long id){
        orderService.delete(id);
        return "redirect:/cabinet";
    }

    @PostMapping("/pay")
    public String pay(@RequestParam long id) throws IOException {
        Order order = orderService.findById(id);
        if (order.isPaid()){
            return "redirect:/cabinet";
        }
        orderService.pay(order);
        return "redirect:/cabinet";
    }



}
