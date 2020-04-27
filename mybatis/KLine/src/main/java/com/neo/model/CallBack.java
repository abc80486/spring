package com.neo.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CallBack implements Serializable{

    private static final long serialVersionUID = 1782377866L;

    private long latelyTime;

    private int cycle;

    private int k;

    private int n;

    private List<CallBackRate> rate;

}
