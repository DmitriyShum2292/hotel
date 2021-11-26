package com.example.hotels.controller;

import com.example.hotels.dto.ResidentReponseDTO;
import com.example.hotels.model.User;
import com.example.hotels.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/admin/user")
public class UserInfoController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public String userInfo(@PathVariable long id, Model model) throws IOException {
        User user = userService.findCurrentUser();
        User userInfo = userService.findById(id);
        ResidentReponseDTO residentReponseDTO = userService.getCitizenInfo(userInfo);
        model.addAttribute("user",user);
        model.addAttribute("user_info",residentReponseDTO);
        return "user_info";
    }

}
