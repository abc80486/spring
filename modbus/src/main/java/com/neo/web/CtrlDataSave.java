package com.neo.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.neo.mapper.ServicesCtrlMapper;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

@RestController
@Configurable
@EnableScheduling
public class CtrlDataSave {

    //@Autowired
    private ServicesCtrlMapper servicesCtrlMapper;

    public CtrlDataSave(ServicesCtrlMapper servicesCtrlMapper){
        this.servicesCtrlMapper = servicesCtrlMapper;
    }
    static long dataNum = -1l;
    static long wp1Date = new Date().getTime();
    static List<Object> dataLast = null;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    @Scheduled(cron = "* * * * * ?")
    public String analysisDataSave() {
        // long wp1Date=0l;
        Date now = new Date();
        List<Object> data = new ArrayList<>(40);
        if (dataNum == -1l) {
            for (int i = 0; i < 40; i++) {
                data.add(-1);
            }
            wp1Date = new Date().getTime();
            dataLast = new ArrayList<>(data);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            servicesCtrlMapper.insertStatusData(dataNum);
            servicesCtrlMapper.insertTest(data);
        }
        data.clear();
        data.add(ScadaImpl.coil01[4]);
        data.add(ScadaImpl.coil01[1]);
        data.add(ScadaImpl.coil01[0]);

        data.add(ScadaImpl.coil01[85]);
        data.add(ScadaImpl.coil01[7]);
        data.add(ScadaImpl.coil01[6]);

        data.add(ScadaImpl.coil01[74]);
        data.add(ScadaImpl.coil01[71]);
        data.add(ScadaImpl.coil01[70]);

        data.add(ScadaImpl.coil01[81]);
        data.add(ScadaImpl.coil01[78]);
        data.add(ScadaImpl.coil01[79]);
        data.add(ScadaImpl.coil01[77]);

        data.add(ScadaImpl.coil01[28]);
        data.add(ScadaImpl.coil01[25]);
        data.add(ScadaImpl.coil01[26]);
        data.add(ScadaImpl.coil01[24]);
        long temp = 0;
        int len = data.size();

        for(int i=0;i<len;i++){
            if((boolean)data.get(i) == true) temp += 2<<len-1-i;
        }
            if((boolean)data.get(1)==true) {
                servicesCtrlMapper.updateDataWP1(new Date().getTime()-wp1Date, dateFormat.format(new Date()));
                if(dataNum == -1 || (boolean)dataLast.get(1)!=true) servicesCtrlMapper.updateDataWP1Times(dateFormat.format(now));
                //System.out.println("电机1运行时间增加。。。");
            }

            if((boolean)data.get(4)==true) {
                servicesCtrlMapper.updateDataWP2(now.getTime()-wp1Date, dateFormat.format(now));
                if(dataNum == -1 || (boolean)dataLast.get(4)!=true) servicesCtrlMapper.updateDataWP2Times(dateFormat.format(now));
                //System.out.println("电机2运行时间增加。。。");

            }

            if((boolean)data.get(7)==true) {
                servicesCtrlMapper.updateDataWP3(now.getTime()-wp1Date, dateFormat.format(now));
                if(dataNum == -1 || (boolean)dataLast.get(7)!=true) servicesCtrlMapper.updateDataWP3Times(dateFormat.format(now));
               // System.out.println("电机3运行时间增加。。。");

            }

            //System.out.println(now.getTime()-wp1Date);
        if(temp!=dataNum) {
            get();
            servicesCtrlMapper.insertStatusData(temp);
            servicesCtrlMapper.insertTest(data);
            System.out.println("状态数据改变，已经保存。。。");
            dataNum = temp;
        }

        dataLast = new ArrayList<>(data);
        dataLast.add(now);

        wp1Date = new Date().getTime();
        return data+"</br>"+dataNum+"</br>"+dataLast;
    }
    
    public String get(){
        Date date = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd");
        timeFormat.format(date);
        String a = servicesCtrlMapper.queryTime(timeFormat.format(date));
        if(a==null || a=="")  servicesCtrlMapper.insertTime(timeFormat.format(date));
        return timeFormat.format(date);
    }

}