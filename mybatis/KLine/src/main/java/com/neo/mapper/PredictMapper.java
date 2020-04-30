package com.neo.mapper;

import java.util.List;

import com.neo.model.Predict;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PredictMapper {

    @Insert("INSERT INTO predict(T,startTime,endTime,predictValue,rate,resultValue,result) VALUES(${T},${startTime},${endTime},${predictValue},${rate},${resultValue},${result})")
    boolean insert(Predict p);

    @Select("SELECT * FROM predict WHERE T= ${value} ORDER BY endTime desc limit 1")
    Predict selectForCycle(int T);
    
    @Update("UPDATE predict SET resultValue = ${resultValue} , result = ${result} WHERE id = ${id} ")
    void update(int id,double resultValue,int result);//根据id更新表字段

    @Select("SELECT * FROM predict WHERE from_unixtime(endTime/1000+1*60*60)<current_timestamp() AND result=0 ")
    List<Predict> getPredicted();    //获取预测结束且没有预测结果的数据

    @Select("SELECT COUNT(result) FROM predict WHERE result = 1")
    int numCorrect();

    @Select("SELECT COUNT(result) FROM predict WHERE result = -1")
    int numError();





}