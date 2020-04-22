package com.neo.services.kline;

import com.neo.model.*;
import java.util.List;

public interface GrowthRateService {

    public List<List<Double>> calGrowthRate(List<MinuteData> d, int[] T);

    public List<Double> calGrowthRate(List<MinuteData> d,int[] T,int s);
    
    public List<Double> calGRforTimes(List<MinuteData> d,int T);

}