package com.neo.services.MinuteRateService.Impl;

import java.util.List;
import javax.annotation.Resource;
import com.neo.mapper.MinuteRateMapper;
import com.neo.model.MinuteRate;
import com.neo.services.MinuteRateService.MinuteRateService;
import org.springframework.stereotype.Service;

@Service
public class MinuteRateServiceImpl implements MinuteRateService {

    @Resource
    MinuteRateMapper minuteDataMapper;

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
        return minuteDataMapper.get(time,num);
    }

    @Override
    public List<MinuteRate> get(Long time) {
        return minuteDataMapper.get(time);
    }
}
