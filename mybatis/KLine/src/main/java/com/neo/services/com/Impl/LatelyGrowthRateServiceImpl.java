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
import com.neo.services.Predict.PredictService;
import com.neo.services.com.LatelyGrowthRateService;
import com.neo.services.util.GetKlineData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LatelyGrowthRateServiceImpl implements LatelyGrowthRateService {
   
    @Autowired
    private MinuteDataService mds;

    @Autowired
    private MinuteRateService mrs;

    @Autowired
    private PredictService ps;

    @Override
    public void updateData(){
        int[] low={1,4,16,24,48,96,192,384,672,960,1344,2808,4320,5616};
        List<MinuteRate> latelyGr = update();

        for(int i=0;i<low.length;i++){
            mrs.update(latelyGr.get(i),low[i]);
        }
        System.out.println(new Date()+" 60天内的增长率数据更新成功");
        //System.out.println(latelyGr);
        List<Predict> re = ps.predict(latelyGr);
        //System.out.println(latelyGr);
        //System.out.println(re);
        int k=0;
        for(int i=0;i<re.size();i++){
            try{
                Predict p = ps.selectForCycle(re.get(i).getT());
                if(p==null || re.get(i).getEndTime() > p.getEndTime())
                    {ps.insert(re.get(i));++k;}
            }catch(Exception e){
                System.out.println(e.toString());
                return;
            }
        }
        System.out.println(new Date()+" predict表添加预测数目："+k);
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

}