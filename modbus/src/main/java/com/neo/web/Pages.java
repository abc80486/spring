package com.neo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Pages{
    @GetMapping("/index")
    public String index(Model model){
        return "index.html";
    }
}