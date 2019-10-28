package com.example.demo.pojo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class test2 {

    //传入greeting对象实现封装
    /*
    @GetMapping("/view")
    public String greetingForm(Model model) {
        model.addAttribute("exdata", new EXData());
        return "echarts/view.html";
    }
    //将封装的对象转入result.html
    
    @PostMapping("/view")//@ModelAttribute
    public String greetingSubmit( EXData exdata) {
        exdata.setPCT(10);
        System.out.print(exdata.getPCT());
        return "/";
       // return "../";
    }
    */
    

}