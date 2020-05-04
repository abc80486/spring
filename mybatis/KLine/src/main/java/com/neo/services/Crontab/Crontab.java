package com.neo.services.Crontab;

import com.neo.model.MinuteData;
import com.neo.services.MinuteDataService.MinuteDataService;
import com.neo.services.Predict.PredictService;
import com.neo.services.com.LatelyGrowthRateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class Crontab {

    @Autowired
    private MinuteDataService mds;

    @Autowired
    private PredictService ps;

    @Autowired
    private LatelyGrowthRateService lgrs;

    @Scheduled(cron = "0 16,31,46,01 * * * ?")
    private void MinuteDataCron(){
        mds.updateDate();
    }

    @Scheduled(cron = "5 16,31,46,01 * * * ?")
    private void PredictCron(){
        ps.updateResult();
    }

    @Scheduled(cron = "10 16,31,46,01 * * * ?")
    private void LatelyGrowthRateCron(){
        lgrs.updateData();
    }

}