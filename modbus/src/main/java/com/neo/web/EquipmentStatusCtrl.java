package com.neo.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neo.mapper.EquipmentStatusMapper;
import com.neo.model.EquipmentStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/EquipmentStatus")
public class EquipmentStatusCtrl{

    @Autowired
    EquipmentStatusMapper equipmentStatusMapper;

    @GetMapping(value="getLast")
    public EquipmentStatus getLast() {
        return equipmentStatusMapper.getLast();
    }
    
    @GetMapping(value="getByTime")
    public List<EquipmentStatus> getByTime(String s,String e){
        return equipmentStatusMapper.getByTime(s, e);
    }

    @GetMapping(value="getOnLineNumber")
    public Map<String,Integer> getOnLineNumber(){
        Map<String,Integer> data = new HashMap<String,Integer>();
        EquipmentStatus temp = equipmentStatusMapper.getLast();
        int run=0;
        int stop=0;int outline=2;
        if(temp.isWp1_status()==true) ++run; else ++stop;
        if(temp.isWp2_status()==true) ++run; else ++stop;
        if(temp.isWp3_status()==true) ++run; else ++stop;
        //if(temp.isValve1_mode()==true) ++run; else ++stop;
        //if(temp.isValve2_mode()==true) ++run; else ++stop;
        data.put("running", run);
        data.put("stopping", stop);
        data.put("outline", outline);

        return data;
    }
    
}