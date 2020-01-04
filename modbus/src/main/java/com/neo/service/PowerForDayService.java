package com.neo.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.neo.mapper.PowerForDayMapper;
import com.neo.shiro.model.PowerForDay;
import com.neo.web.ScadaImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class PowerForDayService {
    //存储累计值
    @Autowired
    PowerForDayMapper powerForDayMapper;

    @Autowired


    public  void  save(){
       // SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        PowerForDay powerForDay = new PowerForDay();
        //powerForDay.setTime(new Date());
        powerForDay.setWp1power(Integer.toString(ScadaImpl.register03[66]));
        powerForDay.setWp2power(ScadaImpl.data.get(32).toString());
        powerForDay.setWp3power(ScadaImpl.data.get(34).toString());

        powerForDayMapper.insert(powerForDay);
    }
}