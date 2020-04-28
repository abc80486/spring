package com.neo.mapper;

import com.neo.model.Predict;

import org.apache.ibatis.annotations.Insert;

public interface PredictMapper {

    @Insert("INSERT INTO predict(T,startTime,endTime,predictValue,rate,resultValue,result) VALUES(${T},${startTime},${endTime},${predictValue},${rate},${resultValue},${result})")
    boolean insert(Predict p);
}