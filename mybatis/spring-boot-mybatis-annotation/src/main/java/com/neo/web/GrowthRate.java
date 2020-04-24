package com.neo.web;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo.model.MinuteData;
import com.neo.model.User;
import com.neo.services.MinuteDataService.MinuteDataService;
import com.neo.services.kline.GrowthRateService;
import com.neo.mapper.MinuteDataMapper;
import com.neo.mapper.UserMapper;

import java.util.Date;

@RestController
public class GrowthRate {

    @Autowired
    private MinuteDataService mds;


    @RequestMapping("/get")
    public List<MinuteData> get() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

        int[] T = {12};
        long ti=0;
        try {
            ti = sdf.parse("2020-01-01 00:00:00").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<MinuteData> d = mds.get(ti, 2000);
        return d;

        //return "yes";
    
    }



}