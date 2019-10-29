package com.example.demo.pojo;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.chain_data.data;

public class EXData {
//data 
/*
1.OBJECTJson{
    GW11:[],
    GW12:[],
    GW13:[],
    A71:[],
    V8:[]
}
2.
*/
     static double PCT = 2.0;
     static double LCT = 3.0;
     static double ECT = 7.0;
     static double HCT = 7.0;

     static double  GW11 = 4000.0;
     static double  GW12 = 3200.0;
     static double  GW13 = 800.0;
     static double A71 = 2400.0;
     static double V8 = 2400.0;

   // List<Double> SR = new ArrayList<Double>();
     static double PSR = 0.0;
     static double LSR = 0.0;
    static double ESR = 0.0;
    static double HSR = 0.0;


    public static double PSR12 = 0.0;
    public static double LSR12 = 0.0;
    public static double ESR12 = 0.0;
    public static double HSR12 = 0.0;

    public static double PSR13 = 0.0;
    public static double LSR13 = 0.0;
    public static double ESR13 = 0.0;
    public static double HSR13 = 0.0;

    public static List<Double> PSRA71 = new ArrayList<>();
    public static List<Double> PSRV8 = new ArrayList<>();   
    public static List<Double> list = new ArrayList<Double>();
    public void test(){
        list.add(1.0);
        list.add(2.0);
        list.add(3.0);
        list.add(4.0);
        PSRA71 = data.getSRa71(A71, 7, 7, 7, 7);
        PSRV8 = data.getSRv8(V8, 7, 7, 7, 7);
        System.out.println(PSRA71);
        System.out.println(PSRV8);
    }
    
/*
    public static double gw11sum = PSR + LSR + ESR +HSR;
    public static double gw12sum = PSR12 + LSR12 + ESR12 +HSR12;
    public static double gw13sum = PSR + LSR + ESR +HSR;
    public static double a71sum = PSR + LSR + ESR +HSR;
    public static double v8sum = PSR + LSR + ESR +HSR;
*/

    public static void main(String[] args) {
        EXData a = new EXData();
        a.get();
        //System.out.println(PSR13);
    }


    public  void get() {
        List<Double> storage = new ArrayList<Double>();
       // data a = new data();
        storage = data.getStorage(GW11, PCT, LCT, ECT, HCT);
        System.out.println(storage);
        PSR = storage.get(0);
        LSR = storage.get(1);
        ESR = storage.get(2);
        HSR = storage.get(3); 

        storage = data.getSRgw12(GW12, PCT, LCT, ECT, HCT);
        System.out.println(storage);
        PSR12 = storage.get(0);
        LSR12 = storage.get(1);
        ESR12 = storage.get(2);
        HSR12 = storage.get(3);  
        //System.out.println(" "+GW13 + PCT + LCT + ECT + HCT);
        storage = data.getSRgw13(GW13, PCT, LCT, ECT, HCT);
        System.out.println(storage);
        //System.out.println(" "+GW13 + PCT + LCT + ECT + HCT);
        PSR13 = storage.get(0);
        LSR13 = storage.get(1);
        ESR13 = storage.get(2);
        HSR13 = storage.get(3);   
    }
    public double getPSR() {
        return PSR;
    }
    public void setPSR(double PSR) {
        this.PSR = PSR;
    }
    public double getLSR() {
        return LSR;
    }
    public void setLSR(double LSR) {
        this.LSR = LSR;
    }
    public double getESR() {
        return ESR;
    }
    public void setESR(double ESR) {
        this.ESR = ESR;
    }
    public double getHSR() {
        return HSR;
    }
    public void setHSR(double HSR) {
        this.HSR = HSR;
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
    public double getGW11() {
        return GW11;
    }
    public void setGW11(double GW11) {
        this.GW11 = GW11;
    }
    public double getGW12() {
        return GW12;
    }
    public void setGW12(double GW12) {
        this.GW12 = GW12;
    }
    public double getGW13() {
        return GW13;
    }
    public void setGW13(double GW13) {
        this.GW13 = GW13;
    }

    public double getA71() {
        return A71;
    }
    public void setA71(double A71) {
        this.A71 = A71;
    }
    public double getV8() {
        return V8;
    }
    public void setV8(double V8) {
        this.V8 = V8;
    }

  
}