package com.neo.web;

import java.util.List;
import com.neo.mapper.ServicesCtrlMapper;
import com.neo.model.ServicesCtrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/equipment")
@EnableScheduling 
public class ServicesCtrlController{
    
    @Autowired
    private ServicesCtrlMapper servicesCtrlMapper;

    @RequestMapping("/getAll")
    public List<ServicesCtrl> getAll(){
        List<ServicesCtrl> temp = servicesCtrlMapper.getAll();
        return temp;
    }

    @RequestMapping("/add2")
    public String insert(ServicesCtrl servicesCtrl,BindingResult result){
        servicesCtrlMapper.insert(servicesCtrl);
        return "DATA INSERT SUCCESS.";
    }

    @RequestMapping(value="/getService", method=RequestMethod.GET)
    public  List<ServicesCtrl>  getService(short service) {
        List<ServicesCtrl> temp = servicesCtrlMapper.getService(service);
        return temp;
    }

    //@GetMapping("/insertDB")
    @Scheduled(cron = "* * * * * ?")
    public String analysisDataSave(){
        return new CtrlDataSave(servicesCtrlMapper).analysisDataSave();
    }
}
