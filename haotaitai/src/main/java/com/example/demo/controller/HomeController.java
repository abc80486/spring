package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/welcome")
public class HomeController {

 
		@RequestMapping(value = "/index",method = RequestMethod.GET)
		public ModelAndView toIndex(){
		  ModelAndView mv = new ModelAndView("index");     
	   	  return mv;  
		}
	
	
	/*
	@RequestMapping(value="/home/page")
	@ResponseBody
	public ModelAndView goHome(){
		System.out.println("go to the home page!");
		ModelAndView mode = new ModelAndView();
		mode.addObject("name", "zhangsan");
		mode.setViewName("index");
		return mode;
	}
	*/
}