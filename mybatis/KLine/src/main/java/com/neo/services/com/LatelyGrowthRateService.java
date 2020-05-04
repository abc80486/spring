package com.neo.services.com;

import java.util.List;

import com.neo.model.MinuteRate;

public interface LatelyGrowthRateService {

    List<MinuteRate> update();
    
    void updateData();
}