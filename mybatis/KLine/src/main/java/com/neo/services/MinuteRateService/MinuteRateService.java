package com.neo.services.MinuteRateService;

import com.neo.model.MinuteRate;
import java.util.List;

public interface MinuteRateService {

    void insert(MinuteRate d);

    void delete(Long time);

    List<MinuteRate> get(Long time,int num);

    List<MinuteRate> get(Long time);

    void update(MinuteRate d,int val);

}