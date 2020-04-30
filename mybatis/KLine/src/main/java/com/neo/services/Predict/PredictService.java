package com.neo.services.Predict;

import java.util.List;

import com.neo.model.Predict;

public interface PredictService {

    boolean insert(Predict p);//向表插入数据
    
    Predict selectForCycle(int T);//根据参数获取表数据

    boolean update(int id,double resultValue,int result);//根据id更新表字段

    List<Predict> getPredicted();    //获取预测结束且没有预测结果的数据

    //根据Predict数据列表计算Predict表中resultValue、result的值，并将字段更新到表
    boolean updateResult();

    //计算预测成功的概率
    double correctRate();


}