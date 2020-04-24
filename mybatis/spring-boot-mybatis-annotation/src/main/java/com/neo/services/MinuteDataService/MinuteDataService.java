package com.neo.services.MinuteDataService;

import com.neo.model.*;
import java.util.List;

public interface MinuteDataService {

    List<MinuteData> get(long stime,long etime);

    List<MinuteData> get(long time,int num) ;//根据开始时间及数量获取数据

    List<MinuteData> get(long time) ;//根据时间获取数据

    MinuteRate getGrowthRate(List<MinuteData> d,int low,int high);//根据数据列表获取T间隔的振幅

    List<MinuteRate> getGrowthRate(List<MinuteData> d,int T);//根据数据列表获取T间隔的振幅

    List<MinuteRate> getGrowthRate(long time,int T);//根据时间和间隔获取振幅列表

    List<Double> getCallBack(List<MinuteRate> d,int k);

    Double getCallBackPro(long stime,long etime,int T,double f,int k,int n);


}