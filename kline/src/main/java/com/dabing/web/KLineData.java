package com.dabing.web;

import java.util.List;

import com.dabing.pojo.MinuteData;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KLineData {

    @RequestMapping("/LatelyData")
    public List<MinuteData> LatelyData(int limit){
        return GetKlineData.getLately("BTCUSDT", "1m", limit);
    }

    @RequestMapping("/LatelyData15m")
    public List<MinuteData> LatelyData15m(int limit){
        return GetKlineData.getLately("BTCUSDT", "15m", limit);
    }

}