package com.neo.web;

import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neo.model.CallBack;
import com.neo.model.MinuteData;
import com.neo.model.MinuteRate;
import com.neo.services.CallBack.CallBackService;
import com.neo.services.MinuteDataService.MinuteDataService;
import com.neo.services.com.LatelyGrowthRateService;
import com.neo.services.util.GetKlineData;

@RestController
public class GrowthRate {

    @Autowired
    private MinuteDataService mds;


    @Autowired
    private CallBackService cbs;

    @Autowired
    private LatelyGrowthRateService lgrs;

    @RequestMapping("/getLatelyGrowthRate")
    public List<MinuteRate> getLatelyGrowthRate(){
        List<MinuteRate> re;
        re = lgrs.update();
        return re;
    }

    @RequestMapping("/predict")
    public boolean predict(){
        return lgrs.predict();
    }
    @RequestMapping("/get")
    public List<MinuteData> get() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

        //int[] T = {4};
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
    @RequestMapping("/updateCallBackRate")
    public boolean updateCallBackRate(long stime,long etime,int T,int k,int n) {

        return cbs.updateCallBack(mds.getCallBackPro(stime, etime, T, k, n));
        //http://localhost:8080/CallBackRate?st=2018-01-01%2000:00:00&et=2020-01-01%2000:00:00&T=16&f=10&k=1&n=2
    }
    @RequestMapping("/updateCallBackRateAll")
    public boolean updateCallBackRate(int T,int k,int n){
        long now = new Date().getTime();
        try{
            updateCallBackRate(0,now,T,k,n);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    @RequestMapping("/CallBackRate")
    public CallBack CallBackRate(String st,String et,int T,int k,int n) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

        long stime=0;
        long etime=0;
        try {
            stime = sdf.parse(st).getTime();
            etime = sdf.parse(et).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CallBack cb = mds.getCallBackPro(stime, etime, T, k, n);
        //System.out.println(cb.getRate().size());
        return cb;
        //http://localhost:8080/callBack?st=2018-01-01%2000:00:00&et=2020-01-01%2000:00:00&T=16&f=10&k=1&n=2
    }
}