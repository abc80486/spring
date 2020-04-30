package com.neo.services.Predict.Impl;

import java.util.Date;
import java.util.List;

import com.neo.mapper.PredictMapper;
import com.neo.model.MinuteData;
import com.neo.model.Predict;
import com.neo.services.MinuteDataService.MinuteDataService;
import com.neo.services.Predict.PredictService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
public class PredictServiceImpl implements PredictService {

    @Autowired
    private PredictMapper pm;

    @Autowired
    private MinuteDataService mds;

    // 向predict表插入数据
    @Override
    public boolean insert(Predict p) {
        return pm.insert(p);
    }

    // 根据表中参数T获取表数据
    @Override
    public Predict selectForCycle(int T) {
        return pm.selectForCycle(T);
    }

    @Override
    public boolean update(int id, double resultValue, int result) {
        try {
            pm.update(id, resultValue, result);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
        
    }

    @Override
    public List<Predict> getPredicted() {
        return pm.getPredicted();
    }

    @Override
    public boolean updateResult() {
        List<Predict> lp = getPredicted();
        int size = lp.size();
        try{
            for(int i=0;i<size;i++){
                Predict p = lp.get(i);
                List<MinuteData> d = mds.get(p.getStartTime(), p.getT());
                if(d.size()!=p.getT()){
                    System.out.println(new Date()+" 成交数据未更新");
                    return false;
                }
                double resultValueLow = d.get(0).getLow_price(),resultValueHigh = d.get(0).getTop_price();
                int result;
                for(int j=1;j<d.size();j++){
                    double m = d.get(j).getLow_price();
                    double n = d.get(j).getTop_price();
                    if(m<resultValueLow) resultValueLow = m;
                    if(n>resultValueHigh) resultValueHigh = n;
                }
                if(p.getRate()<=0) {
                    if(resultValueLow <= p.getPredictValue()) result = 1;
                    else result = -1;
                    update(p.getId(), resultValueLow, result);
                    //System.out.println(resultValueHigh+" "+result);
                }else{
                    if(resultValueHigh >= p.getPredictValue()) result = 1;
                    else result = -1;
                    update(p.getId(), resultValueHigh, result);               
                }

            }
        }catch(Exception e){
            System.out.println(new Date()+"predict表更新预测结果失败");
            return false;
        }
        System.out.println(new Date()+" predict表更新预测结果数量 " + size);
        return true;
    }

   
    @RequestMapping("/predict/correctRate")
    @Override
    public double correctRate() {
        int y = pm.numCorrect();
        int n = pm.numError();
        
        return y*100.0/(y+n);
    }

}