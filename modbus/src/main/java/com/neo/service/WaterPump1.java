package com.neo.service;

import com.neo.web.ScadaImpl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class WaterPump1 extends WaterPump {
    private final static int CTRLADDR = 4;
    private final static int STATUSADDR = 2;
    private final static int MODEADDR = 1;
    private final static int POWER=5000;

    WaterPump1(){
        init();
    }
    public static void main(String[] args){
        System.out.println(new WaterPump1().getStatus());
    }

    public long getDayRunTime(){

        return dayRunTime; 
    }
    public void init(){
        //setStatus(ScadaImpl.coil01[STATUSADDR]==true?(int)1:(int)0);
        setMode(1);
        setStatus(1);
    }

}