package com.neo.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    static long wp1Date = new Date().getTime();
    static List<Object> dataLast = null;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    @GetMapping("/insertDB")
    public String test2(){
        //long wp1Date=0l;
        Date now = new Date();
        List<Object> data = new ArrayList<>(40);
        if(dataNum == -1l)   {
            for(int i=0;i<40;i++){
                data.add(-1);
            }
            wp1Date =  new Date().getTime();
            dataLast = new ArrayList<>(data);
            servicesCtrlMapper.insertStatusData(dataNum);
            servicesCtrlMapper.insertTest(data);
        }
        data.clear();
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
            if((boolean)data.get(i) == true) temp += 2<<len-1-i;
        }
        //dataLast = new ArrayList<>(data);
/*
        if(ScadaImpl.coil01[1]==true) {
            get();
            
            List<String> datawp1 = new ArrayList<>();
            datawp1.add(Long.toString(now.getTime()-wp1Date));
            int tem;
            if(dataNum == -1 || (boolean)dataLast.get(1)!=true) tem = 1;
            else tem = 0;
            datawp1.add(Integer.toString(tem));
            datawp1.add(dateFormat.format(now));
            servicesCtrlMapper.updatawp1(datawp1);
            
            //servicesCtrlMapper.updateDataWP1(now.getTime()-wp1Date, dateFormat.format(now));
            //if(dataNum == -1 || (boolean)dataLast.get(1)!=true) servicesCtrlMapper.updateDataWP1Times(dateFormat.format(now));
        }*/
            if(ScadaImpl.coil01[1]==true) {
                servicesCtrlMapper.updateDataWP1(now.getTime()-wp1Date, dateFormat.format(now));
                if(dataNum == -1 || (boolean)dataLast.get(1)!=true) servicesCtrlMapper.updateDataWP1Times(dateFormat.format(now));
            }

            if(ScadaImpl.coil01[7]==true) {
                servicesCtrlMapper.updateDataWP2(now.getTime()-wp1Date, dateFormat.format(now));
                if(dataNum == -1 || (boolean)dataLast.get(4)!=true) servicesCtrlMapper.updateDataWP2Times(dateFormat.format(now));
            }

            if(ScadaImpl.coil01[71]==true) {
                servicesCtrlMapper.updateDataWP3(now.getTime()-wp1Date, dateFormat.format(now));
                if(dataNum == -1 || (boolean)dataLast.get(7)!=true) servicesCtrlMapper.updateDataWP3Times(dateFormat.format(now));
            }

            //System.out.println(now.getTime()-wp1Date);
        if(temp!=dataNum) {
            get();
            servicesCtrlMapper.insertStatusData(temp);
            servicesCtrlMapper.insertTest(data);

            dataNum = temp;
        }

        dataLast = new ArrayList<>(data);
        dataLast.add(now);

        wp1Date = new Date().getTime();
        return data+"</br>"+dataNum+"</br>"+dataLast;
    }
    private  List<ServicesCtrl>  getOpeByTime(Date start,Date end) {
       SimpleDateFormat a = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
       List<ServicesCtrl> temp = servicesCtrlMapper.getOperaInTime(a.format(start),a.format(end));
       return temp;
    }
    @GetMapping("/getRuntime")
    public Map<String,List<Integer>> getRuntime(){
        Map<String,List<Integer>> data = new HashMap<String,List<Integer>>();

        return data;
    }
    @GetMapping("/test")
    public String get(){
        //String a = servicesCtrlMapper.queryTime("2019/12/24");
        //判断是否存在数据
        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd");
        timeFormat.format(date);
    
        String a = servicesCtrlMapper.queryTime(timeFormat.format(date));
        //创建数据
        if(a==null || a=="")  servicesCtrlMapper.insertTime(timeFormat.format(date));

        //servicesCtrlMapper.updateDataWP1(time,timeFormat.format(date));
        return timeFormat.format(date);
    }

}