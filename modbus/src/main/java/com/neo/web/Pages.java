package com.neo.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Pages{
   // @GetMapping("/index")
    //@RequiresPermissions("userInfo:view")//权限管理;
    //public String index(Model model){
       // return "index.html";
    //}

    @GetMapping("/TrendAnalysis")
    //@RequiresPermissions("userInfo:view")//权限管理;
    //@ResponseBody
    public String TrendAnalysis(Model model){
        return "TrendAnalysis.html";
    }



}