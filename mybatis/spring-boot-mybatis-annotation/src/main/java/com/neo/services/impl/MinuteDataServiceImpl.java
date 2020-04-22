package com.neo.services.impl;

import java.util.List;

import javax.annotation.Resource;

import com.neo.mapper.MinuteDataMapper;
import com.neo.model.MinuteData;
import com.neo.services.MinuteDataService;

import org.springframework.stereotype.Service;

@Service
public class MinuteDataServiceImpl implements MinuteDataService {

    @Resource
	private MinuteDataMapper data;
    
    @Override
    public  List<MinuteData> getByTimeNum(long time , int num) {
        List<MinuteData> d = data.getByTimeNum(time, num);
        if(d.size()==num)
            return d;
        else {
            System.out.println("数据量不足");
            return null;
        }
    }

}