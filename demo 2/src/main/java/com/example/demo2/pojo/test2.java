package com.example.demo.pojo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.chain_data.data;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class test2 {

    //传入greeting对象实现封装
    public static int pcr = 12;
    @GetMapping("/test")
    public String greetingForm(Model model) {
        //int[] titles = new int[]{47, 47, 21};
        List<Double> a = new ArrayList<>();
        a.add(1.0);
        a.add(2.0);
        a.add(3.0);
        int power = 50;
        AS pr = new AS();
        model.addAttribute("titles",a);
        model.addAttribute("power",power);
        model.addAttribute("pr",pr);
        JSONObject dataAll = new JSONObject();
        dataAll.put("sr11", data.getStorage(4000,2,3,7,7));
        dataAll.put("sr12", data.getSRgw12(4000,2,3,7,7));
        dataAll.put("sr13", data.getSRgw13(4000,2,3,7,7));
        dataAll.put("sr71", data.getSRa71(4000,2,3,7,7));
        dataAll.put("sr8", data.getSRv8(4000,2,3,7,7));
        dataAll.getJSONArray("sr11");
        model.addAttribute("dataall",dataAll);
        return "echarts/test.html";
    }
    //将封装的对象转入result.html
    
    @PostMapping("/test")//@ModelAttribute
    public void greetingSubmit(Model model ,AS pr) {
        //exdata.setPCT(10);
       // System.out.print(pr.pr);
       // pcr = (int)pr + 1 ;
       //model.addAttribute("pr",pr);
       greetingForm(model);
       // return "echarts/test.html";
       // return "../";
    }   
}
class AS{
    public int pr=12;
    public int getpr(){
        return pr;
    }
    public void setpr(int pr){
        this.pr = pr;
    }

}