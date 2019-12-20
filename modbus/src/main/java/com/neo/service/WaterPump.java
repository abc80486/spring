package com.neo.service;

import java.util.Date;

public class WaterPump implements DeviceInterface {
    private int status;
    private String name;
    private int mode;

    public long dayRunTime;
    public long weekRunTime;
    public long monthRunTime;

    public double dayElectricityConsumption;
    public double weekElectricityConsumption;
    public double monthElectricityConsumption;

    //private double ratedPower;
    private double power;
    private int ctrl;

    public long getDayRunTime(){
        return this.dayRunTime;
    }
    public long weekRunTime(){
        return this.weekRunTime;
    }
    public long monthRunTime(){
        return this.monthRunTime;
    }
    public double dayElectricityConsumption(){
        return this.dayElectricityConsumption;
    }
    public double weekElectricityConsumption(){
        return this.weekElectricityConsumption;
    }
    public double monthElectricityConsumption(){
        return this.monthElectricityConsumption;
    }



    @Override
    public int getStatus() {
        //注意
        if(this.mode==1) return this.ctrl;
        return this.status;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getMode() {
        return this.mode;
    }

    @Override
    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setStatus(int status) {
        if(this.mode==1){
            setCtrl(status);
        }else{
            System.out.println(new Date().toString()+" 手动模式不允许操作");
        }
    }
    public int getCtrl(){
        return this.ctrl;
    }
    public void setCtrl(int ctrl){
        this.ctrl = ctrl;
    }


    public double getPower(){
        return this.power;
    }
    public void setPower(double power){
        this.power = power;
    }

}