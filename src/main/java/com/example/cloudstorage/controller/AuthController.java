package com.example.cloudstorage.controller;

import com.example.cloudstorage.model.User;
import com.example.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value = "/signup")
    public String signUp(){return "signup";}

    @PostMapping(value = "/register")
    public String register(@ModelAttribute("SpringWeb") User user){
        if (user == null){
            return "redirect:signup";
        }

        try {
            userService.register(user);
        }catch(Exception e){
            return "redirect:signup?error";
        }
        return "redirect:signup?success";
    }
}
