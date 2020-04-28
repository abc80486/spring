package com.neo.services.MinuteDataService.Impl;

import java.util.List;
import java.util.ArrayList;


import com.neo.mapper.MinuteDataMapper;
import com.neo.model.CallBack;
import com.neo.model.CallBackRate;
import com.neo.model.MinuteData;
import com.neo.model.MinuteRate;
import com.neo.services.MinuteDataService.MinuteDataService;
import com.neo.services.util.GetKlineData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MinuteDataServiceImpl implements MinuteDataService {

    @Autowired
    private MinuteDataMapper data;

    @Override
    public List<MinuteData> get(long time, int num) {
        List<MinuteData> d = data.getByTimeNum(time, num);
        if (d.size() == num)
            return d;
        else {
            System.out.println("数据量不足");
            return null;
        }
    }

    @Override
    public List<MinuteData> get(long time) {
        List<MinuteData> d = data.getBySTime(time);
        return d;
    }

    @Override
    public List<MinuteRate> getGrowthRate(List<MinuteData> d, int T) {

        /*
        
        */
        List<MinuteRate> re = new ArrayList<MinuteRate>();
        int size = d.size();
        for (int i = 0; i <= size - T - 1; i++)
            re.add(getGrowthRate(d, i, i + T - 1));// 定值
        for (int i = size - T; i < size; i++)
            re.add(getGrowthRate(d, i, size - 1));// 波动值
        return re;
    }

    @Override
    public MinuteRate getGrowthRate(List<MinuteData> d, int low, int high) {
        MinuteRate re = new MinuteRate();

        /*
         * 1.判断参数。1.1若低位大于等于高位，显示错误信息，返回空值；1.2数据集的长度小于高位数值， 显示错误信息，返回空值；
         * 2.程序模型。从数列中获取最值；设置标志数值为初值；遍历数列,获取最值所在的位置;计算振幅；
         * 
         */
        if (low > high) {
            return null;
        }
        ;
        if (high >= d.size()) {
            return null;
        }
        ;

        double p1 = d.get(low).getLow_price();
        int t1 = low;
        double p2 = d.get(low).getTop_price();
        int t2 = low;

        for (int i = low; i <= high; i++) {
            if (d.get(i).getLow_price() < p1) {
                p1 = d.get(i).getLow_price();
                t1 = i;
            }
            if (d.get(i).getTop_price() > p2) {
                p2 = d.get(i).getTop_price();
                t2 = i;
            }
        }

        double range;
        if (t1 < t2)
            range = (p2 - p1) / p1 * 100;
        else if (t1 == t2) {
            if (d.get(t1).getOpen_price() < d.get(t1).getClose_price())
                range = (p2 - p1) / p1 * 100;
            else
                range = (p1 - p2) / p2 * 100;
        } else
            range = (p1 - p2) / p2 * 100;

        re.setStart_time(d.get(low).getStart_time());
        re.setEnd_time(d.get(high).getEnd_time() + 1);
        re.setLow_price(p1);
        re.setHigh_price(p2);
        re.setLow_time(d.get(t1).getStart_time());
        re.setHigh_time(d.get(t2).getStart_time());
        re.setRange_price(range);

        return re;
    }

    @Override
    public List<MinuteData> get(long stime, long etime) {
        return data.getBySDTime(stime, etime);
    }

    @Override
    public List<MinuteRate> getGrowthRate(long time, int T) {
        return getGrowthRate(get(time), T);
    }

    @Override
    public List<Double> getCallBack(List<MinuteRate> d, int k) {
        // 数据中增长率间隔必须大于给定T
        // MinuteRate[] re=null;
        List<Double> re = new ArrayList<>();
        for (int i = 0; i < d.size() - k; i++) {

            double p1 = d.get(i).getRange_price(), p2 = d.get(i).getHigh_price();
            double p3 = d.get(i).getLow_price();

            double m = Double.MAX_VALUE, n = Double.MIN_VALUE;
            int flag = 0;
            for (int j = i + 1; j <= i + k; j++) {
                if (d.get(j).getStart_time() > d.get(i).getHigh_time()) {
                    m = Math.min(d.get(j).getLow_price(), m);
                    n = Math.max(d.get(j).getHigh_price(), n);
                    flag = 1;
                }
            }
            double p;

            if (p1 >= 0)
                p = ((m - p2) / p2);
            else
                p = ((n - p3) / p3);

            if (flag == 1)
                re.add(p * 100);
            /*
             * if(d.get(i).getRange_price()>=2.0 || d.get(i).getRange_price()<=-2.0){
             * System.out.println(d.get(i)+" "+p*100); if(p1>=0) System.out.println(m); else
             * System.out.println(m); }
             */
        }

        return re;
    }

    @Override
    public CallBack getCallBackPro(long stime, long etime, int T, int k, int n) {

        CallBack re = new CallBack();
        List<MinuteData> sd = get(stime, etime);
        List<MinuteRate> d = getGrowthRate(sd, T);
        List<Double> cb = getCallBack(d, k * T);

        List<CallBackRate> cbr = new ArrayList<CallBackRate>();

        re.setStartTime(sd.get(0).getStart_time());
        re.setLatelyTime(sd.get(sd.size()-1).getEnd_time()+1);
        re.setCycle(T);
        re.setK(k);
        re.setN(n);
        cbr.add(null);
        for(int f=1;f<=14;f++){
            int cun = 0, sum = 0;
            for (int i = 0; i < cb.size(); i++) {
                double tag = d.get(i).getRange_price();
                if (tag >= f || tag <= -1.0 * f) {
                    sum++;
                    if (tag >= 0 && cb.get(i) <= -1.0 * f / n) {
                        cun++;
                    }
                    if (tag < 0 && cb.get(i) >= 1.0*f / n) {
                        cun++;
                    }
                }
            }
            CallBackRate temp = new CallBackRate();
            temp.setCun(cun);temp.setSum(sum);temp.setP(sum!=0?cun*1.0/sum*100:0);
            cbr.add(temp);
        }
        re.setRate(cbr);
        return re;
    }

    @Override
    public Double getCallBackPro(long stime, long etime, int T, double f, int k, int n) {
        List<MinuteData> sd = get(stime, etime);
        List<MinuteRate> d = getGrowthRate(sd, T);
        List<Double> cb = getCallBack(d, k * T);
        int cun = 0, sum = 0;
        for (int i = 0; i < cb.size(); i++) {
            double tag = d.get(i).getRange_price();
            if (tag >= f || tag <= -1.0 * f) {
                // System.out.print(d.get(i)+" ");
                sum++;
                if (tag >= 0 && cb.get(i) <= -1.0 * f / n) {
                    cun++;
                    // System.out.print(cb.get(i));
                }
                if (tag < 0 && cb.get(i) >= f / n) {
                    cun++;
                    // System.out.print(cb.get(i));
                }
                // System.out.println("");
            }
            //
        }
        System.out.println(sd.size() + "," + d.size() + "," + cb.size());
        System.out.println(cun + "," + sum + "," + cun * 1.0 / sum * 100);
        return cun * 1.0 / sum * 100;
    }

    @Override
    public MinuteData insert(MinuteData d) {
        data.insert(d);
        return d;
    }

    @Override
    public boolean insertFor15m(int limit) {
        // 获取数据库最新数据的标志
        long DBLatelyTime = data.getLatelyData();
        // 获取数据库最新数据的下一条数据；确保下一条数据的准确，可丢弃所获取的最新数据
        List<MinuteData> d = GetKlineData.Kline_15m(DBLatelyTime + 15 * 60 * 1000, limit);
        // 将所获得数据写入数据库
        try {
            for (int i = 0; i < d.size() - 1; i++) {
                data.insert(d.get(i));
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }



}