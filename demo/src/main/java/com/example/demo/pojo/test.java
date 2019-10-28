package com.example.demo.pojo;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.chain_data.data;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class test {

    //将封装的对象转入result.html
    @GetMapping("/view")
    public String greetingForm(Model model) {
        model.addAttribute("exdata", new EXData());
        return "echarts/view.html";
    }
    @PostMapping("/view")
    public String greeForm(Model model,EXData exdata) {
        //Model model=null;
        
        //exdata.setPCT(10);
        List<Double> storage = new ArrayList<Double>();
        storage = data.getStorage(exdata.GW11, exdata.PCT, exdata.LCT, exdata.ECT, exdata.HCT);
        exdata.setPSR(storage.get(0));
        exdata.setLSR(storage.get(1));
        exdata.setESR(storage.get(2));
        exdata.setHSR(storage.get(3));
        model.addAttribute("exdata", exdata);
        return "echarts/view.html";
    }

    

}