package com.example.demo.pojo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class test2 {

    //传入greeting对象实现封装
    
    @GetMapping("/test")
    public String greetingForm(Model model) {
        int[] titles = new int[]{47, 47, 21};
        int power = 50;
        model.addAttribute("titles",titles);
        model.addAttribute("power",power);
        return "echarts/test.html";
    }
    //将封装的对象转入result.html
    
    @PostMapping("/test")//@ModelAttribute
    public String greetingSubmit( EXData exdata) {
        exdata.setPCT(10);
        System.out.print(exdata.getPCT());
        return "/";
       // return "../";
    }
    
    

}