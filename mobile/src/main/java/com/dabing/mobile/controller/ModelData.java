package com.dabing.mobile.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ModelData {

    //将封装的对象转入result.html
    @GetMapping("/view")
    public String getMainForm(Model model) {
        return "hbuild/index.html";
    }
    @PostMapping("/view2")
    public String postForm(Model model) {
        model.addAttribute("exdata", "123");
        return "hbuild/index.html";
    }

    

}