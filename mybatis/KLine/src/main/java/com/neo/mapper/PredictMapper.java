package com.neo.mapper;

import java.util.List;

import com.neo.model.Predict;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.lang.Nullable;

public interface PredictMapper {

    @Insert("INSERT INTO predict(T,startTime,endTime,predictValue,rate,resultValue,result) VALUES(${T},${startTime},${endTime},${predictValue},${rate},${resultValue},${result})")
    boolean insert(Predict p);

    @Select("SELECT * FROM predict WHERE T= ${value} ORDER BY endTime desc limit 1")
    Predict selectForCycle(int T);
    
    @Update("UPDATE predict SET resultValue = ${resultValue} , result = ${result} WHERE id = ${id} ")
    void update(int id,double resultValue,int result);//根据id更新表字段

    @Select("SELECT * FROM predict WHERE result=0")
    List<Predict> getPredicted();    //获取预测结束且没有预测结果的数据

    @Select("SELECT COUNT(result) FROM predict WHERE result = 1")
    int numCorrect();

    @Select("SELECT COUNT(result) FROM predict WHERE result = -1")
    int numError();

    @Select("SELECT COUNT(result) FROM predict WHERE result = 1 AND T = ${value}")
    int numCorrectOnCycle(int T);

    @Select("SELECT COUNT(result) FROM predict WHERE result = -1 AND T = ${value}")
    int numErrorOnCycle(int T);

    //time时间内的预测指数，单位毫秒，如24小时内的平均预测价格，time = 24*60*60*1000
    @Select("")
    double predictIndex(long time);






}