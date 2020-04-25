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
import com.neo.model.MinuteRate;
import com.neo.model.User;
import com.neo.services.MinuteDataService.MinuteDataService;
import com.neo.services.com.LatelyGrowthRateService;
import com.neo.services.kline.GrowthRateService;
import com.neo.mapper.MinuteDataMapper;
import com.neo.mapper.UserMapper;

import java.util.Date;

@RestController
public class GrowthRate {

    @Autowired
    private MinuteDataService mds;


    @Autowired
    private LatelyGrowthRateService lgrs;

    @RequestMapping("/getLatelyGrowthRate")
    public List<MinuteRate> getLatelyGrowthRate(){
        List<MinuteRate> re;
        re = lgrs.update();
        return re;
    }

    @RequestMapping("/get")
    public List<MinuteData> get() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

        int[] T = {4};
        long ti=0;
        long t2=0;
        try {
            ti = sdf.parse("2020-04-01 00:00:00").getTime();
            t2 = sdf.parse("2020-04-18 00:00:00").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<MinuteData> d = mds.get(t2);
        //List<MinuteRate> gr = mds.getGrowthRate(d, 12);

        //mds.getCallBack(gr, 12);
        
        return d;

        //return "yes";
    
    }
    @RequestMapping("/cb")
    public double callback() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

        long stime=0;
        long etime=0;
        try {
            stime = sdf.parse("2020-01-01 00:00:00").getTime();
            etime = sdf.parse("2020-04-18 00:00:00").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mds.getCallBackPro(stime, etime, 12, 4, 1, 2);
    }




}