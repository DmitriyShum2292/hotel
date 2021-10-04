package com.example.hotels.controller;

import com.example.hotels.dto.Credentials;
import com.example.hotels.model.User;
import com.example.hotels.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String registerPage(Model model){
        model.addAttribute("user",new User());
        return "registration";
    }

    @PostMapping
    public String register(@ModelAttribute("user") User user){
        User userExist = userService.findByUserId(user.getId());
        if (userExist!=null){
        return "redirect:/cabinet";
        }
        user.setActive(true);

        userService.save(user);
        return "redirect:/cabinet";
    }
}
