package com.example.hotels.controller;

import com.example.hotels.exception.NotFoundException;
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
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.time.LocalDateTime;


/**
 * Controller for simple user cabinet
 */
@Controller
@RequestMapping("/cabinet")
public class CabinetController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    private static final String HOTELS = "hotels";
    private static final String REDIRECT_CAB = "redirect:/cabinet";

    /**
     * @param page
     * @param size
     * @param name
     * @param sort
     * @param model
     * return cabinet page with hotel list and order list
     */
    @GetMapping
    public String cabinet(@RequestParam(required = false,defaultValue = "0") int page,
                          @RequestParam(required = false,defaultValue = "10") int size,
                          @RequestParam(required = false,defaultValue = "0") String name,
                          @RequestParam(required = false, defaultValue = "name") String sort, Model model){
        User user = userService.findCurrentUser();
        if(!name.equals("0")) {
            model.addAttribute(HOTELS, hotelService.findAllByNameStartingWithIgnoreCase(name));
        }
        else {
            Page<Hotel> all = hotelService.findAll(page,size,sort);
            if (all.isEmpty()){throw new NotFoundException("No hotels found");}
            model.addAttribute(HOTELS, hotelService.findAll(page, size, sort));
        }
        model.addAttribute("user",user);
        model.addAttribute("orders",userService.findOrdersByUser());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        return "cabinet";
    }

    /**
     * @param page
     * @param size
     * @param sort
     * @param model
     * return cabinet with hotels paginated and sorted
     */
    @GetMapping("/{page}")
    public String cabinetPagination(@PathVariable int page,
                          @RequestParam(required = false,defaultValue = "10") int size,
                          @RequestParam(required = false, defaultValue = "name") String sort, Model model){
        User user = userService.findCurrentUser();
        Page<Hotel> all = hotelService.findAll(page,size,sort);
        if (all.isEmpty()){throw new NotFoundException("No hotels found");}
        model.addAttribute("user",user);
        model.addAttribute(HOTELS,all);
        model.addAttribute("orders",userService.findOrdersByUser());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        return "cabinet";
    }

    /**
     * @param hotel
     * @param model
     * return order page
     */
    @GetMapping("/order/{hotel}")
    public String createOrderPage(@PathVariable String hotel , Model model){
        User user = userService.findCurrentUser();
        model.addAttribute("user",user);
        Hotel foundHotel = hotelService.findByName(hotel);
        if (foundHotel == null){throw new NotFoundException("Hotel not found");}
        model.addAttribute("hotel",foundHotel);
        return "order";
    }

    /**
     * @param hotelName
     * @param period
     * @param booking
     * return redirect to cabinet after order saved
     */
    @PostMapping("/order")
    public String createOrder(@RequestParam String hotelName ,@RequestParam int period ,
                              @RequestParam String booking) {
        Hotel hotel = hotelService.findByName(hotelName);
        LocalDateTime dateTime = LocalDateTime.parse(booking);
        Order order = new Order(hotel, dateTime,period);
        orderService.save(order);
        return REDIRECT_CAB;
    }

    /**
     * @param id
     * return redirect to cabinet after deleting order
     */
    @GetMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable long id){
        orderService.delete(id);
        return REDIRECT_CAB;
    }

    /**
     * @param id
     * return redirect to payment by url from citizen account
     * @throws IOException
     */
    @PostMapping("/pay")
    public RedirectView pay(@RequestParam long id) throws IOException {
        Order order = orderService.findById(id);
        if (order.isPaid()){
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(REDIRECT_CAB);
            return redirectView;
        }
        String url = orderService.pay(order);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(url);
        return redirectView;
    }



}
