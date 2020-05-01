package com.neo.services.com.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.neo.mapper.PredictMapper;
import com.neo.model.MinuteData;
import com.neo.model.MinuteRate;
import com.neo.model.Predict;
import com.neo.services.MinuteDataService.MinuteDataService;
import com.neo.services.MinuteRateService.MinuteRateService;
import com.neo.services.com.LatelyGrowthRateService;
import com.neo.services.util.GetKlineData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Service
public class LatelyGrowthRateServiceImpl implements LatelyGrowthRateService {
   
    @Autowired
    private MinuteDataService mds;

    @Autowired
    private MinuteRateService mrs;

    @Autowired
    private PredictMapper pm;

    @Scheduled(cron = "10 16,31,46,01 * * * ?")
    public void updateData(){
        int[] low={1,4,16,24,48,96,192,384,672,960,1344,2808,4320,5616};
        List<MinuteRate> latelyGr = update();

        for(int i=0;i<low.length;i++){
            mrs.update(latelyGr.get(i),low[i]);
        }
        System.out.println(new Date()+" 最近一周的增长率更新成功");

        List<Predict> re = predict(latelyGr);
        for(int i=0;i<re.size();i++){
            try{
                Predict p = pm.selectForCycle(re.get(i).getT());
                if(p==null || re.get(i).getEndTime() > p.getEndTime())
                    pm.insert(re.get(i));
            }catch(Exception e){
                System.out.println(e.toString());
                return;
            }
        }
        System.out.println(new Date()+" 最近一周的价格预测更新成功");
    }

    public List<MinuteRate> update(){
        String[] val={"15m","1h","4h","6h","12h","1d","2d","4d","7d","10d","14d","30d","45d","60d"};
        int[] low={1,4,16,24,48,96,192,384,672,960,1344,2808,4320,5616};
        long  time = new Date().getTime()-65*24*60*60*1000l;
        List<MinuteData> d = mds.get(time);
        //List<MinuteData> d = GetKlineData.Kline_15m(time);
        int high = d.size()-1;
        List<MinuteRate> re= new ArrayList<MinuteRate>();
        for(int i=0;i<low.length;i++){
            MinuteRate k = mds.getGrowthRate(d, high-low[i]+1, high);
            //mrs.update(k,low[i]);
            re.add(k);
        }
        return re;
    }
    public List<Predict> predict(List<MinuteRate> mr){
        List<Predict> re = new ArrayList<Predict>();
        int[] T={1,4,16,24,48,96,192,384,672,960,1344};
        int[] P={1,2, 4, 4, 6, 6,  8, 10, 12, 12,  15};
        for(int i=1;i<T.length;i++){
            MinuteRate tp = mr.get(i);
            //for(int j=14;j>=2;j-=2){
                if(tp.getRange_price()>=P[i]){
                    Predict p = new Predict();
                    p.setT(T[i]);
                    int k = 15*60*1000;
                    p.setStartTime(tp.getHigh_time()+k);
                    p.setEndTime(tp.getHigh_time()+k*T[i]+k);
                    int n=2;
                    p.setPredictValue(tp.getHigh_price()*(1-tp.getRange_price()/n/100));
                    p.setRate(tp.getRange_price()/2*-1);
                    re.add(p);
                    break;
                }
                if(tp.getRange_price()<=P[i]*-1){
                    Predict p = new Predict();
                    p.setT(T[i]);
                    int k = 15*60*1000;
                    p.setStartTime(tp.getLow_time()+k);
                    p.setEndTime(tp.getLow_time()+k*T[i]+k);
                    int n=2;
                    p.setPredictValue(tp.getLow_price()*(1-tp.getRange_price()/n/100));
                    p.setRate(tp.getRange_price()/2*-1);
                    re.add(p);
                    break;
                }
            //}
        }
        return re;
    }


    public boolean predict(){
        List<MinuteRate> mr = update();
        List<Predict> re = predict(mr);
        for(int i=0;i<re.size();i++){
            try{
                Predict p = pm.selectForCycle(re.get(i).getT());
                if(p==null || re.get(i).getEndTime() > p.getEndTime())
                    pm.insert(re.get(i));
            }catch(Exception e){
                System.out.println(e.toString());
                return false;
            }
        }
        return true;
    }

}