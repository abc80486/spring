package com.example.demo.pojo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DataRF {

    //将封装的对象转入result.html
    @GetMapping("/view")
    public String getMainForm(Model model) {
        EXData exdata = new EXData();
        exdata.get();
        model.addAttribute("exdata", exdata);
        return "echarts/view2.html";
    }
    @PostMapping("/view")
    public String postForm(Model model,EXData exdata) {
        exdata.get();
        model.addAttribute("exdata", exdata);
        return "echarts/view2.html";
    }

    

}