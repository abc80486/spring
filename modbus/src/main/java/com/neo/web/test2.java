package com.neo.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling 
@Controller
public class test2 {

    //传入greeting对象实现封装
    public static int pcr = 12;
    //@Scheduled(cron = "* * * * * ?")
    public void add(){
        pcr++;
    }
    @GetMapping("/test")
    public String greetingForm(Model model) {
        //int[] titles = new int[]{47, 47, 21};
        List<Double> a = new ArrayList<>();
        a.add(1.0);
        a.add(2.0);
        a.add(3.0);
        int power = 50;
        AS PR = new AS();
        pcr++;
       //String pr =   getSData.fileIn("test.txt");
       //String pr;
        model.addAttribute("titles",a);
        model.addAttribute("power",power);
        model.addAttribute("pr",pcr);
        return "test::model";
    }

    //将封装的对象转入result.html
    
    @PostMapping("/test")//@ModelAttribute
    public String greetingSubmit(Model model ,AS PR) {
     greetingForm(model);
     
      System.out.println(PR.getpr());
       PR.setpr(13);
      model.addAttribute("pr",PR);
      return "test.html";
       // return "../";
    } 
    @RequestMapping("/hello") 
    public String hello(HttpServletRequest request, @RequestParam(value = "power", defaultValue = "springboot-thymeleaf") String name) { 
        request.setAttribute("power", name); 
        return "test";
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