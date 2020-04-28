package com.dabing.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class MinuteData implements Serializable {

	private static final long serialVersionUID = 17877866L;

	private int id;
	private long start_time;
	private double open_price;
	private double top_price;
	private double low_price;
	private double close_price;
	private double amount;
	private long end_time;

	private double quantity;
	private double exchange_lots;

	private double vol_amount;
	private double vol_quantity;

	public MinuteData() {
		super();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ""+new Date(this.start_time) + this.close_price + "  " + this.amount ;
	}

}