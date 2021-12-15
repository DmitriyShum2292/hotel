package com.example.hotels.controller;

import com.example.hotels.dto.BuildingInfoDTO;
import com.example.hotels.dto.CommercialBuildingDTO;
import com.example.hotels.dto.DistrictDTO;
import com.example.hotels.dto.StreetDTO;
import com.example.hotels.exception.NotFoundException;
import com.example.hotels.logging.Loggable;
import com.example.hotels.model.Hotel;
import com.example.hotels.model.Order;
import com.example.hotels.model.User;
import com.example.hotels.service.HotelService;
import com.example.hotels.service.OrderService;
import com.example.hotels.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for Admin cabinet(Hotel manager)
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private OrderService orderService;

    private static final  String HOTEL_NAME = "hotel";
    private static final String REDIRECT_ADMIN = "redirect:/admin";
    private static final String CREATE_HOTEL = "create_hotel";
    private static final String ORDERS = "orders";

    /**
     * @param name
     * @param model
     * return admin page with his hotel and orders
     */
    @GetMapping
    public String adminPage(@RequestParam(required = false,defaultValue = "0") String name, Model model){
        User user = userService.findCurrentUser();
        if(!name.equals("0")){
            User searchUser = userService.findByNickName(name);
            if (searchUser==null){
                model.addAttribute(ORDERS,new ArrayList<Order>());
            }
            model.addAttribute(ORDERS,orderService
                    .findAllByHotelAndUser(user.getHotel(),searchUser));
        }
        else {
            model.addAttribute(ORDERS,orderService.findByHotel(user.getHotel()));
        }
        model.addAttribute("user", user);
        model.addAttribute(HOTEL_NAME, user.getHotel());
        return "admin_cabinet";
    }

    /**
     * @param districtId
     * @param streetId
     * @param buildingId
     * @param homeId
     * @param model
     * return choosing a district page,then choosing a street page,
     * then choosing building page, then create hotel page
     * @throws IOException
     */
    @GetMapping("/hotel")
    @Loggable
    public String createHotelPage(@RequestParam(required = false) Long districtId,
                                  @RequestParam(required = false) Long streetId,
                                  @RequestParam(required = false)Long buildingId,
                                  @RequestParam(required = false)Long homeId, Model model) throws IOException {
        User user = userService.findCurrentUser();
        model.addAttribute("user", user);
        if (districtId==null&&streetId==null&&buildingId==null){
            List<DistrictDTO> districtDTOS = hotelService.getAllDistricts();
            if (districtDTOS.isEmpty()){
                throw new NotFoundException("Districts not found");
            }
            model.addAttribute("districts",districtDTOS);
            return "district";
        }
        if(districtId!=null){
            List<StreetDTO> streetDTOS = hotelService.getAllStreetsByDistrict(districtId);
            if (streetDTOS.isEmpty()){
                throw new NotFoundException("Streets not found in this district");
            }
            model.addAttribute("streets",streetDTOS);
            return "street";
        }
        if (streetId!=null){
            List<CommercialBuildingDTO> commercialBuildingDTOS = hotelService.getAllCommercialBuildingByStreet(streetId);
            if (commercialBuildingDTOS.isEmpty()){
                throw new NotFoundException("Commercial buildings not found on this street");
            }
            model.addAttribute("buildings",commercialBuildingDTOS);
            return "building";
        }
        if (buildingId!=null){
            List<BuildingInfoDTO> buildingInfoDTOS = hotelService.getAllBuildingInfoByCommercialBuilding(buildingId);
            if (buildingInfoDTOS.isEmpty()){
                throw new NotFoundException("You can't create a hotel here!");
            }
            model.addAttribute("homeId",buildingInfoDTOS.get(0).getId());

            Hotel hotel = new Hotel();
            model.addAttribute("user", user);
            model.addAttribute(HOTEL_NAME, hotel);

            return CREATE_HOTEL;
        }
        Hotel hotel = new Hotel();

        model.addAttribute(HOTEL_NAME, hotel);

        return CREATE_HOTEL;
    }

    /**
     * @param hotel
     * @param bindingResult
     * @param model
     * return redirect to admin page after saving hotel
     * @throws IOException
     */
    @PostMapping("/hotel")
    public String createHotel(@ModelAttribute("hotel") @Valid Hotel hotel, BindingResult bindingResult,
                              Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error",bindingResult.getFieldErrors());
            User user = userService.findCurrentUser();
            model.addAttribute("user", user);
            return CREATE_HOTEL;
        }
        User user = userService.findCurrentUser();
        hotelService.saveWithVerification(hotel);
        user.setHotel(hotel);
        userService.save(user);
        return REDIRECT_ADMIN;
    }

    /**
     * @param model
     * return update hotel page
     */
    @GetMapping("/hotel/update")
    public String updateHotelPage(Model model){
        User user = userService.findCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute(HOTEL_NAME, user.getHotel());
        return "update_hotel";
    }

    /**
     * @param hotel
     * return redirect to admin cabinet after hotel update
     */
    @PostMapping("/hotel/update")
    public String updateHotel(@ModelAttribute("hotel")Hotel hotel){
        hotelService.update(hotel);
        return REDIRECT_ADMIN;
    }

    /**
     * @param id
     * return redirect to admin cabinet after hotel delete
     */
    @GetMapping("/hotel/{id}")
    public String deleteHotel(@PathVariable long id){
        hotelService.delete(id);
        return REDIRECT_ADMIN;
    }
}
