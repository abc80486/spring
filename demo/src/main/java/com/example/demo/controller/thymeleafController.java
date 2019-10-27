package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("th")
public class thymeleafController {
    @RequestMapping("home")
    public String home(){
       // System.out.println("redirect to home page!");
        return "thymeleaf/index.html";
    }
}