package com.neo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Pages{
    @GetMapping("/index")
    //@ResponseBody
    public String index(Model model){
        return "index.html";
    }

    @GetMapping("/TrendAnalysis")
    //@ResponseBody
    public String TrendAnalysis(Model model){
        return "TrendAnalysis.html";
    }



}