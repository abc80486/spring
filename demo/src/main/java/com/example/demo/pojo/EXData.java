package com.example.demo.pojo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.chain_data.Data;

public class EXData {
      double PCT = 2.0;
      double LCT = 3.0;
      double ECT = 7.0;
      double HCT = 7.0;
      int  GW11 = 4000;
      int  GW12 = 3200;
      int  GW13 = 800;
      int A71 = 2400;
      int V8 = 2400;
      double A71CT = 7;
      double V8CT = 7;
      JSONObject Indata = new JSONObject();
    public  List<Double> PSRA71 = new ArrayList<>();
    public  List<Double> PSRV8 = new ArrayList<>();  
    public  List<Double> PSRGW11 = new ArrayList<>();
    public  List<Double> PSRGW12 = new ArrayList<>();  
    public  List<Double> PSRGW13 = new ArrayList<>();
    public static void main(String[] args){
        EXData a = new EXData();
        a.get();
    } 
    public static List<Double> list = new ArrayList<Double>();
    public  void  get(){   
        Data data = new Data();
        PSRA71 = data.getSRa71(A71, A71CT, A71CT, A71CT, A71CT);
        PSRV8 = data.getSRv8(V8,V8CT , V8CT, V8CT, V8CT);
        PSRGW11 = data.getSRgw11(GW11, PCT, LCT, ECT, HCT);
        PSRGW12 = data.getSRgw12(GW12, PCT, LCT, ECT, HCT);
        PSRGW13 = data.getSRgw13(GW13, PCT, LCT, ECT, HCT);
        System.out.println(PSRGW11);
        System.out.println(PSRGW12);
        System.out.println(PSRGW13);
        System.out.println(PSRA71);
        System.out.println(PSRV8);
        //----------------------------
        //-----------------------------


    }
    public double getA71CT() {
        return A71CT;
    }
    public void setA71CT(double A71CT) {
        this.A71CT = A71CT;
    }
    public double getV8CT() {
        return V8CT;
    }
    public void setV8CT(double V8CT) {
        this.V8CT = V8CT;
    }

    public double getPCT() {
        return PCT;
    }
    public void setPCT(double PCT) {
        this.PCT = PCT;
    }

    public double getLCT() {
        return LCT;
    }
    public void setLCT(double LCT) {
        this.LCT = LCT;
    }
    public double getECT() {
        return ECT;
    }
    public void setECT(double ECT) {
        this.ECT = ECT;
    }
    public double getHCT() {
        return HCT;
    }
    public void setHCT(double HCT) {
        this.HCT = HCT;
    }
    public int getGW11() {
        return GW11;
    }
    public void setGW11(int GW11) {
        this.GW11 = GW11;
    }
    public int getGW12() {
        return GW12;
    }
    public void setGW12(int GW12) {
        this.GW12 = GW12;
    }
    public int getGW13() {
        return GW13;
    }
    public void setGW13(int GW13) {
        this.GW13 = GW13;
    }

    public int getA71() {
        return A71;
    }
    public void setA71(int A71) {
        this.A71 = A71;
    }
    public int getV8() {
        return V8;
    }
    public void setV8(int V8) {
        this.V8 = V8;
    }

  
}