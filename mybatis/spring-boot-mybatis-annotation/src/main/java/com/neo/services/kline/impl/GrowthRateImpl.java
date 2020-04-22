package com.neo.services.kline.impl;

import java.util.List;

import javax.annotation.Resource;

import java.util.ArrayList;

import com.neo.model.MinuteData;
import com.neo.services.MinuteDataService;
import com.neo.services.kline.GrowthRateService;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

@Service
public class GrowthRateImpl implements GrowthRateService {


    @Override
    public List<Double> calGrowthRate(List<MinuteData> d, int[] T, int s) {
        //输入参数中T必须按照从小到大排列；
        //注意增长率的方向的判定。1.根据高低价格的时间，2.根据开收盘价
        //
        List<Double> re = new ArrayList<Double>();

        int size = T[T.length-1];//遍历的深度为T数组最后一个数大小

        double high,low;//存放高低位；
        int ht=s,lt=s;

        if(d.size()<size+s) {
            System.out.println("数据量太少");
            return null;
        }

        high = d.get(s).getTop_price();
        low = d.get(s).getLow_price();

        //re.add(d.get(s).getStart_time()/1.0);

        int j=0;
        for(int i=s+1;i<s+size;i++){

            double h = d.get(i).getTop_price();
            double l = d.get(i).getLow_price();
            double r = 0;

            if(h > high){
                high = h; ht = i;
            }
            if(l < low){
                low = l; lt = i;
            }
            if(j>=T.length) break;
            else if(i-s== T[j]-1){
                j++;
                if(ht == lt){ //判断增长的方向
                    if(d.get(ht).getOpen_price() <= d.get(ht).getClose_price()){ //开大于闭
                        r = (high - low) / low;
                    }else r = (low - high) / high; //闭大于开
                }else if(ht > lt){
                    r = (high - low) / low;
                }else r = (low - high) / high;

                re.add(r*100);
            }
        }
        re.add(d.get(s).getStart_time()/1.0);
        return re;
    }


    public List<List<Double>> calGrowthRate(List<MinuteData> d, int[] T){

        //int[] T = { 4, 8, 16, 24, 48, 96, 192, 384, 672 };
        List<List<Double>> re = new ArrayList<List<Double>>();
        
        int l = T.length;
        int k = T[l-1];
        
        for(int i=0;i<d.size()-k;i++){
            List<Double> a = calGrowthRate(d, T, i);
            re.add(a);
        }

        for(int i=0;i<re.size()-8;i++){
            for(int j=0;j<l;j++){
                if(re.get(i).get(j) >= 2*1.0){
                    //计算从当前时间范围内回调范围
                    System.out.println(re.get(i));
                    for(int m=i;m<i+T[j];m++){
                        if(re.get(m).get(j) <=-1){
                            System.out.println(re.get(m));
                        }
                    }

                    System.out.println("--------");

                }
            }
        }
        return re;  
    }


    @Override
    public List<Double> calGRforTimes(List<MinuteData> d, int T) {
        // TODO Auto-generated method stub
        return null;
    }
}