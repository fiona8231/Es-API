package com.example.fiona.managementsystem.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Login {

    @RequestMapping("/h")
    public String index(){
        return "redirect:/login.html";
    }
}
