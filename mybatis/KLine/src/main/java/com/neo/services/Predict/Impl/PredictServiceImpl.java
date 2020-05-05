package com.neo.services.Predict.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.neo.mapper.PredictMapper;
import com.neo.model.MinuteData;
import com.neo.model.MinuteRate;
import com.neo.model.Predict;
import com.neo.services.MinuteDataService.MinuteDataService;
import com.neo.services.Predict.PredictService;
import com.neo.services.com.LatelyGrowthRateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
public class PredictServiceImpl implements PredictService {

    @Autowired
    private PredictMapper pm;

    @Autowired
    private MinuteDataService mds;

    @Autowired
    private LatelyGrowthRateService lgrs;
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
        int num=0;
        try{
            for(int i=0;i<size;i++){
                Predict p = lp.get(i);
                List<MinuteData> d = mds.get(p.getStartTime());
                //mds.getGrowthRate(d, low, high)
                if(d.size()!=p.getT()){
                    //System.out.println(new Date()+" 成交数据未更新");
                    //return false;
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
                    if(resultValueLow <= p.getPredictValue()) {result = 1;}
                    else if(p.getEndTime()<new Date().getTime()) result = -1;else continue;
                    update(p.getId(), resultValueLow, result);
                    //System.out.println(resultValueHigh+" "+result);
                }else{
                    if(resultValueHigh >= p.getPredictValue()) {result = 1;}
                    else if(p.getEndTime()<new Date().getTime()) result = -1;else continue;
                    update(p.getId(), resultValueHigh, result);               
                }
                ++num;
            }
        }catch(Exception e){
            System.out.println(new Date()+" predict表更新预测结果失败");
            return false;
        }
        System.out.println(new Date()+" predict表更新预测结果数量：" + num);
        return true;
    }

    public List<Predict> predict(List<MinuteRate> mr){
        List<Predict> re = new ArrayList<Predict>();
        int[] T={1,4,16,24,48,96,192,384,672,960,1344};
        int[] P={1,2, 2, 4, 4, 6,  8, 10, 12, 15,  20};
        for(int i=1;i<T.length;i++){
            MinuteRate tp = mr.get(i);
                if(tp.getRange_price()>=P[i]){
                    Predict p = new Predict();
                    p.setT(T[i]);
                    int k = 15*60*1000;
                    p.setStartTime(tp.getHigh_time()+k);
                    p.setEndTime(tp.getHigh_time()+k*T[i]+k);
                    int n=2;
                    p.setPredictValue(tp.getHigh_price()*(1-tp.getRange_price()/n/100));
                    p.setRate(tp.getRange_price()/2*-1);
                    re.add(p);
                }
                if(tp.getRange_price()<=P[i]*-1){
                    Predict p = new Predict();
                    p.setT(T[i]);
                    int k = 15*60*1000;
                    p.setStartTime(tp.getLow_time()+k);
                    p.setEndTime(tp.getLow_time()+k*T[i]+k);
                    int n=2;
                    p.setPredictValue(tp.getLow_price()*(1-tp.getRange_price()/n/100));
                    p.setRate(tp.getRange_price()/2*-1);
                    re.add(p);
                }
        }
        return re;
    }


    public void predict(){
        List<MinuteRate> mr = lgrs.update();
        List<Predict> re = predict(mr);
        for(int i=0;i<re.size();i++){
            try{
                Predict p = pm.selectForCycle(re.get(i).getT());
                if(p==null || re.get(i).getEndTime() > p.getEndTime())
                    pm.insert(re.get(i));
            }catch(Exception e){
                System.out.println(e.toString());
                return;
            }
        }
    }

   
    @RequestMapping("/predict/correctRate")
    @Override
    public double correctRate() {
        int y = pm.numCorrect();
        int n = pm.numError();
        
        return y*100.0/(y+n);
    }

    @RequestMapping("/predict/correctRate/{T}")
    @Override
    public double correctRate(@PathVariable int T) {
        int y = pm.numCorrectOnCycle(T);
        int n = pm.numErrorOnCycle(T);
        
        return y*100.0/(y+n);
    }


}