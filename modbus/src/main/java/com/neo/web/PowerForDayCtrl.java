package com.neo.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.neo.mapper.PowerForDayMapper;
import com.neo.shiro.model.PowerForDay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/PowerForDay")
public class PowerForDayCtrl {

    @Autowired
    PowerForDayMapper powerForDayMapper;

    @GetMapping(value = "select")
    public PowerForDay select(Date time) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        return powerForDayMapper.selectByTime(f.format(time));
    }
    
    @GetMapping("save")
    public String save(){

        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM//dd");

        PowerForDay powerForDay = new PowerForDay();
        powerForDay.setTime(f.format(new Date()));
        //powerForDay.setId(4);
        powerForDay.setWp1power(ScadaImpl.data.get(30).toString());
        powerForDay.setWp2power(ScadaImpl.data.get(32).toString());
        powerForDay.setWp3power(ScadaImpl.data.get(34).toString());

        powerForDayMapper.insert(powerForDay);
        return powerForDay.toString();
    }
    //获取今天的耗电量
    @GetMapping("power")
    public Map<String,Object> data(){
        Map<String,Object> d = new HashMap<String,Object>();
        PowerForDay last=null;
        try{
             last = select(new Date());
        }catch(Exception e){
            System.out.println("获取电量失败！");
            select(new Date());
        }
        d.put("wp1powerDay", Integer.valueOf(ScadaImpl.data.get(30).toString()) - Integer.valueOf(last.getWp1power()));
        d.put("wp2powerDay", Integer.valueOf(ScadaImpl.data.get(32).toString()) - Integer.valueOf(last.getWp1power()));
        d.put("wp3powerDay", Integer.valueOf(ScadaImpl.data.get(34).toString()) - Integer.valueOf(last.getWp1power()));
        
        
        return d;
    }
    @GetMapping("isPower")
    public boolean ispower(){
        try{
            PowerForDay last = select(new Date());
        }catch(Exception e){
            return false;
        }
        return true;
    }

    public static Set<Date> dateCal(Date time){
        Set<Date> d = new HashSet<Date>();

        return d;
    }

    
}