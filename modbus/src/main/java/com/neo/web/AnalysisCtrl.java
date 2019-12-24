package com.neo.web;

import java.util.List;

import com.neo.mapper.AnalysisDataMapper;
import com.neo.model.AnalysisData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analysisData")
public class AnalysisCtrl {
    @Autowired
    AnalysisDataMapper analysisData;

    @GetMapping("/runtime")
    public List<AnalysisData> getRuntime(String startDate,String endDate){
        return analysisData.getRuntingData(startDate, endDate);
    }
}