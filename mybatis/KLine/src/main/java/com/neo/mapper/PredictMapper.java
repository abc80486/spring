package com.neo.mapper;

import com.neo.model.Predict;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface PredictMapper {

    @Insert("INSERT INTO predict(T,startTime,endTime,predictValue,rate,resultValue,result) VALUES(${T},${startTime},${endTime},${predictValue},${rate},${resultValue},${result})")
    boolean insert(Predict p);

    @Select("SELECT * FROM predict WHERE T= ${value} ORDER BY endTime desc limit 1")
    Predict selectForCycle(int T);
}