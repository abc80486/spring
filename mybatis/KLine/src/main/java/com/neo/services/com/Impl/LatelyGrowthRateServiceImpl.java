package com.neo.services.com.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.neo.model.MinuteData;
import com.neo.model.MinuteRate;
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

    @Scheduled(cron = "20 */16 * * * ?")
    public void updateData(){
        int[] low={1,4,16,24,48,96,192,384,672};
        for(int i=0;i<low.length;i++){
            mrs.update(update().get(i),low[i]);
        }
        System.out.println(new Date()+" 最近一周的增长率更新成功");
    }
    public List<MinuteRate> update(){
        String[] val={"15m","1h","4h","6h","12h","1d","2d","4d","7d"};
        int[] low={1,4,16,24,48,96,192,384,672};
        long  time = new Date().getTime()-8*24*60*60*1000;
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