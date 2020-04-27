package com.neo.services.MinuteRateService.Impl;

import java.util.List;
import com.neo.mapper.MinuteRateMapper;
import com.neo.model.MinuteRate;
import com.neo.services.MinuteRateService.MinuteRateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MinuteRateServiceImpl implements MinuteRateService {

    @Autowired
    private MinuteRateMapper minuteDataMapper;

    @Override
    public void insert(MinuteRate d) {
        minuteDataMapper.insert(d);
    }

    @Override
    public void delete(Long time) {
        minuteDataMapper.delete(time);
    }

    @Override
    public List<MinuteRate> get(Long time, int num) {
        return minuteDataMapper.getByTimeNum(time,num);
    }

    @Override
    public List<MinuteRate> get(Long time) {
        return minuteDataMapper.getByTime(time);
    }

    @Override
    public void update(MinuteRate d, int val) {
        minuteDataMapper.update(d, val);
    }
}
