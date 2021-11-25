package com.example.hotels.controller;

import com.example.hotels.dto.BuildingInfoDTO;
import com.example.hotels.dto.CommercialBuildingDTO;
import com.example.hotels.dto.DistrictDTO;
import com.example.hotels.dto.StreetDTO;
import com.example.hotels.exception.NotFoundException;
import com.example.hotels.model.Hotel;
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
import java.util.List;

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

    @GetMapping
    public String adminPage(Model model){
        User user = userService.findCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute(HOTEL_NAME, user.getHotel());
        return "admin_cabinet";
    }
    @GetMapping("/hotel")
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
    @GetMapping("/hotel/update")
    public String updateHotelPage(Model model){
        User user = userService.findCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute(HOTEL_NAME, user.getHotel());
        return "update_hotel";
    }
    @PostMapping("/hotel/update")
    public String updateHotel(@ModelAttribute("hotel")Hotel hotel){
        hotelService.update(hotel);
        return REDIRECT_ADMIN;
    }
    @GetMapping("/hotel/{id}")
    public String deleteHotel(@PathVariable long id){
        hotelService.delete(id);
        return REDIRECT_ADMIN;
    }
}
