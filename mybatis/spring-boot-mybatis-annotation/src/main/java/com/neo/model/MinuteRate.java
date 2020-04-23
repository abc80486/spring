package com.neo.model;

import java.io.Serializable;

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

}