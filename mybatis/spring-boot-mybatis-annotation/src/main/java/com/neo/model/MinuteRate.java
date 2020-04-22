package com.neo.model;

import java.io.Serializable;

import java.util.Date;
import lombok.Data;

@Data
public class MinuteRate implements Serializable{

    private static final long serialVersionUID = 178778864766L;
    
    private long start_time;
    private double t4;
    private double t8;
    private double t16;
    private double t24;
    private double t48;
    private double t96;
    private double t192;
    private double t384;
    private double t672;

	@Override
	public String toString() {
        return ""+new Date(this.start_time) +" "+ this.t4 + "  " + this.t8 + 
                " "+this.t16 +" "+ this.t24 + "  " + this.t48 + " "+this.t96 + " " +
                this.t192 + " "+this.t384 +" "+ this.t672;
	}
    



}