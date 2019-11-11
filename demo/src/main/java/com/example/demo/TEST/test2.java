package com.example.demo.TEST;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        AS PR = new AS();

       //String pr =   getSData.fileIn("test.txt");
       //String pr;
        model.addAttribute("titles",a);
        model.addAttribute("power",power);
        model.addAttribute("pr",PR);
        
        return "echarts/test.html";
    }
    //将封装的对象转入result.html
    
    @PostMapping("/test")//@ModelAttribute
    public String greetingSubmit(Model model ,AS PR) {
     greetingForm(model);
     
      System.out.println(PR.getpr());
       PR.setpr(13);
      model.addAttribute("pr",PR);
      return "echarts/test.html";
       // return "../";
    } 

 
}
class AS{
    int pr;
    public int getpr(){
        return pr;
    }
    public void setpr(int pr){
        this.pr = pr;
    }

}