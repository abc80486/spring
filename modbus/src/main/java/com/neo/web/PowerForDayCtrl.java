package com.neo.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.neo.mapper.PowerForDayMapper;
import com.neo.shiro.model.PowerForDay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@EnableScheduling   // 2.开启定时任务
@RestController
@RequestMapping("/PowerForDay")
public class PowerForDayCtrl {

    @Autowired
    PowerForDayMapper powerForDayMapper;

    //@GetMapping(value = "select")
    public PowerForDay select(Date time) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        return powerForDayMapper.selectByTime(f.format(time));
    }
    public PowerForDay selectRange(Date sdate,Date edate) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        return powerForDayMapper.selectByTimeRange(f.format(sdate),f.format(edate));
    }
    public PowerForDay selectDay(Date time) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
        return powerForDayMapper.selectByOneDay(f.format(time));
    }

    //@GetMapping("save")
    @Scheduled(cron = "0 1 0 * * ?")
    public  String  save(){

        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM//dd");

        PowerForDay powerForDay = new PowerForDay();
        powerForDay.setTime(f.format(new Date()));
        //powerForDay.setId(4);
        powerForDay.setWp1power(ScadaImpl.data.get(30).toString());
        powerForDay.setWp2power(ScadaImpl.data.get(32).toString());
        powerForDay.setWp3power(ScadaImpl.data.get(34).toString());
        //if(select(new Date())==null)
        try{
            powerForDayMapper.insert(powerForDay);
        }catch(Exception e){
            System.out.println(new Date()+" 能耗统计异常.");
        }
        return powerForDay.toString();
    }
    //获取今天的耗电量
    @GetMapping("power")
    public Map<String,Object> data(){
        Map<String,Object> d = new HashMap<String,Object>();
        int wp1 = Integer.valueOf(ScadaImpl.data.get(30).toString());
        int wp2 = Integer.valueOf(ScadaImpl.data.get(32).toString());
        int wp3 = Integer.valueOf(ScadaImpl.data.get(34).toString());
     

        PowerForDay last=null;
        
        last = select(new Date());
        if(last==null){
            System.out.println(new Date()+"---->>>>  初始化电量累计值！");
            save();
            return data();
            //return null;
        }
        d.put("wp1powerDay", wp1 - Integer.valueOf(last.getWp1power()));
        d.put("wp2powerDay", wp2 - Integer.valueOf(last.getWp2power()));
        d.put("wp3powerDay", wp3 - Integer.valueOf(last.getWp3power()));
        List<Date> date = Util.dateCal(new Date());
        last = select(date.get(0));//
        d.put("wp1powerWeek", wp1 - Integer.valueOf(last.getWp1power()));
        d.put("wp2powerWeek", wp2 - Integer.valueOf(last.getWp2power()));
        d.put("wp3powerWeek", wp3 - Integer.valueOf(last.getWp3power()));
        last = select(date.get(1));//
        d.put("wp1powerMon", wp1 - Integer.valueOf(last.getWp1power()));
        d.put("wp2powerMon", wp2 - Integer.valueOf(last.getWp2power()));
        d.put("wp3powerMon", wp3 - Integer.valueOf(last.getWp3power()));
        PowerForDay tem=null;
        try {
            last = selectRange(date.get(2), date.get(1));
            tem = selectDay(date.get(1));

        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }finally{
            if(last==null || tem==null){
                System.out.println("累计电量为空");
                d.put("wp1powerLastMon", 0);
                d.put("wp2powerLastMon", 0);
                d.put("wp3powerLastMon", 0);  

            }
            else{
                d.put("wp1powerLastMon", Integer.valueOf(tem.getWp1power())-Integer.valueOf(last.getWp1power()));
                d.put("wp2powerLastMon", Integer.valueOf(tem.getWp2power())-Integer.valueOf(last.getWp2power()));
                d.put("wp3powerLastMon", Integer.valueOf(tem.getWp3power())-Integer.valueOf(last.getWp3power()));    
            }
        }
        return d;
    }
}