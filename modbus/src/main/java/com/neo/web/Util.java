package com.neo.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Util{
    public static void main(String[] args){
        //System.out.println(dateCal(new Date()));
    }
    //获得当周的第一天（星期一为第一天）、获得月初日期，获得前三个月1号的日期
    public static List<Date> dateCal(Date time){
        ArrayList<Date> d = new ArrayList<Date>();
        Calendar date = Calendar.getInstance();
        date.setTime(time);
        int year = date.get(Calendar.YEAR);
        int mon = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        int week = date.get(Calendar.DAY_OF_WEEK)-1;
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        Calendar tem = (Calendar) date.clone();
    
		if(date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
			date.add(Calendar.DATE, -1);
		}
		date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        d.add(date.getTime());

        date = (Calendar) tem.clone();
        date.set(Calendar.DAY_OF_MONTH, 1);
        d.add(date.getTime());

        for(int i=0;i<3;i++){
            date.add(Calendar.MONTH, -1);
            d.add(date.getTime());
        }
        date.add(Calendar.MONTH, -9);
        d.add(date.getTime());
        return d;
    }

}