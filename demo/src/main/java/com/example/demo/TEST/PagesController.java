package com.example.demo.TEST;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {
    @GetMapping("/page")
    public String index(Model model) {
        return "pages_model/page_test/index.html";
    }
    @GetMapping("/register")
    public String register(Model model) {
        return "pages_model/page_test/register.html";
    }
    @GetMapping("/login")
    public String login(Model model) {
        return "pages_model/page_test/login.html";
    }

}
