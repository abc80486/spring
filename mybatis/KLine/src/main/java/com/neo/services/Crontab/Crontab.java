package com.neo.services.Crontab;

import com.neo.services.Predict.PredictService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class Crontab {

    @Autowired
    private PredictService ps;

    @Scheduled(cron = "0 17 * * * ?")
    private void PredictCron(){
        ps.updateResult();
    }

}