package com.example.hotels.controller;

import com.example.hotels.dto.Credentials;
import com.example.hotels.model.User;
import com.example.hotels.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String register(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("error",bindingResult.getFieldErrors());
            return "registration";
        }
        User userExist = userService.findByUserId(user.getId());
        if (userExist!=null){
        return "redirect:/cabinet";
        }
        user.setActive(true);

        userService.save(user);
        return "redirect:/cabinet";
    }
}
