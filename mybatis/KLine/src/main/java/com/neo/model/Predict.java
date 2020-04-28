package com.neo.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Predict implements Serializable{

    private static final long serialVersionUID = 422677L;

    private int T;
    private long startTime;
    private long endTime;
    private double predictValue;
    private double rate;
    private double resultValue;
    private int result;

}