package com.neo.services.com.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.neo.model.MinuteData;
import com.neo.model.MinuteRate;
import com.neo.services.MinuteDataService.MinuteDataService;
import com.neo.services.MinuteRateService.MinuteRateService;
import com.neo.services.com.LatelyGrowthRateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LatelyGrowthRateServiceImpl implements LatelyGrowthRateService {
   
    @Autowired
    private MinuteDataService mds;

    @Autowired
    private MinuteRateService mrs;

    public List<MinuteRate> update(){
        String[] val={"1h","4h","6h","12h","1d","2d","4d","7d"};
        int[] low={4,16,24,48,96,192,384,672};
        long  time = new Date().getTime()-10*24*60*60*1000;
        List<MinuteData> d = mds.get(time);
        int high = d.size()-1;
        List<MinuteRate> re= new ArrayList<MinuteRate>();
        for(int i=0;i<low.length;i++){
            MinuteRate k = mds.getGrowthRate(d, high-low[i]+1, high);
            mrs.update(k,low[i]);
            re.add(k);
        }
        return re;
    }



}