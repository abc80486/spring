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
import com.neo.services.MinuteDataService;
import com.neo.services.kline.GrowthRateService;
import com.neo.mapper.MinuteDataMapper;
import com.neo.mapper.UserMapper;

import java.util.Date;

@RestController
public class GrowthRate {

    @Autowired
    private MinuteDataService mds;

    @Autowired
    private GrowthRateService gr;

    @RequestMapping("/getGR")
    public List<Double> getGrowthRate(String time, int num) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

        //int[] T = { 4, 8, 16, 24, 48, 96, 192, 384, 672 };

        int[] T = {4,16,48};
        long ti=0;
        try {
            ti = sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<MinuteData> d = mds.getByTimeNum(ti, 100);
        System.out.println(d.size());
        for(int i=0;i<d.size()-48;i++){
            List<Double> a = gr.calGrowthRate(d, T, i);
            //if(a.get(1)>=2.0 || a.get(1)<-2.0)
            System.out.println(sdf.format(new Date(d.get(i).getStart_time()))+" "+a);
            //System.out.println(sdf.format(new Date(Math.round(a.get(3)))));
        }

        return null;  
    }

    @RequestMapping("/get")
    public List<List<Double>> getGrowth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

        int[] T = {12};
        long ti=0;
        try {
            ti = sdf.parse("2020-01-01 00:00:00").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<MinuteData> d = mds.getByTimeNum(ti, 2000);
        return gr.calGrowthRate(d,T);

        //return "yes";
    
    }



}