package com.neo.web;

import java.util.List;

import com.neo.mapper.ServicesCtrlMapper;
import com.neo.model.ServicesCtrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipment")
public class ServicesCtrlController{
    
    @Autowired
    private ServicesCtrlMapper servicesCtrlMapper;

    @RequestMapping("/getAll")
    public List<ServicesCtrl> getAll(){
        List<ServicesCtrl> temp = servicesCtrlMapper.getAll();
        return temp;
    }

    @RequestMapping("/add")
    public void insert(ServicesCtrl servicesCtrl,BindingResult result){
        servicesCtrlMapper.insert(servicesCtrl);
    }
}