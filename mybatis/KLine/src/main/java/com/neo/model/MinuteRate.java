package com.neo.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

@Data
public class MinuteRate implements Serializable{

    private static final long serialVersionUID = 178778864766L;
    
    private long start_time;
    private long end_time;
    private double low_price;
    private double high_price;
    private long low_time;
    private long high_time;
    private double range_price;

    @Override
	public String toString() {
        // TODO Auto-generated method stub
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

        return ""+sdf.format(new Date(this.start_time)) +" "+sdf.format(new Date(this.end_time)) + "  " + this.low_price
        +" "+this.high_price + " "+ this.range_price;
	}


}