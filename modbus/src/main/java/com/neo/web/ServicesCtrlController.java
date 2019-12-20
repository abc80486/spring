package com.neo.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import com.neo.mapper.ServicesCtrlMapper;
import com.neo.model.ServicesCtrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.SimpleDateFormat;

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
    public String insert(ServicesCtrl servicesCtrl,BindingResult result){
        servicesCtrlMapper.insert(servicesCtrl);
        return "DATA INSERT SUCCESS.";
    }

    @RequestMapping(value="/getService", method=RequestMethod.GET)
    public  List<ServicesCtrl>  getService(short service) {
        List<ServicesCtrl> temp = servicesCtrlMapper.getService(service);
        return temp;
    }

    static long dataNum = -1l;
    @GetMapping("/insertDB")
    public String test2(){
        List<Boolean> data = new ArrayList<>(20);
        if(dataNum == -1l)   {
            servicesCtrlMapper.insertStatusData(dataNum);
        }
        data.add(ScadaImpl.coil01[4]);
        data.add(ScadaImpl.coil01[1]);
        data.add(ScadaImpl.coil01[0]);

        data.add(ScadaImpl.coil01[85]);
        data.add(ScadaImpl.coil01[7]);
        data.add(ScadaImpl.coil01[6]);

        data.add(ScadaImpl.coil01[74]);
        data.add(ScadaImpl.coil01[71]);
        data.add(ScadaImpl.coil01[70]);

        data.add(ScadaImpl.coil01[81]);
        data.add(ScadaImpl.coil01[78]);
        data.add(ScadaImpl.coil01[79]);
        data.add(ScadaImpl.coil01[77]);

        data.add(ScadaImpl.coil01[28]);
        data.add(ScadaImpl.coil01[25]);
        data.add(ScadaImpl.coil01[26]);
        data.add(ScadaImpl.coil01[24]);
        long temp = 0;
        int len = data.size();
        for(int i=0;i<len;i++){
            if(data.get(i) == true) temp += 2<<len-1-i;
        }
        if(temp!=dataNum) {
            servicesCtrlMapper.insertStatusData(temp);
            servicesCtrlMapper.insertTest(data);
            dataNum = temp;
        }
        return data+"</br>"+dataNum;
    }
    private  List<ServicesCtrl>  getOpeByTime(Date start,Date end) {
       SimpleDateFormat a = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
       List<ServicesCtrl> temp = servicesCtrlMapper.getOperaInTime(a.format(start),a.format(end));
       return temp;
    }
}