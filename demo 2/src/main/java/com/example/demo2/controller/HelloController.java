package com.example.demo.controller;
 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
@RestController
public class HelloController {
 
    @RequestMapping("/")
    public String home(){
        JSONObject jsonObject = new JSONObject();
        return "hello!";
    }
}