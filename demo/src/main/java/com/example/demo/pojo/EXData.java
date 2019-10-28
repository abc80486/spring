package com.example.demo.pojo;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.chain_data.data;

public class EXData {

    double PCT = 2.0;
    double LCT = 3.0;
    double ECT = 7.0;
    double HCT = 7.0;

    double GW11 = 4000.0;
    double GW12 = 3200.0;
    double GW13 = 800.0;
    double A71 = 2400.0;
    double A8 = 2400.0;

   // List<Double> SR = new ArrayList<Double>();
     double PSR = 0.0;
     double LSR = 0.0;
     double ESR = 0.0;
     double HSR = 0.0;
    public void getSR(){

        List<Double> storage = new ArrayList<Double>();
        storage = data.getStorage(this.GW11, this.PCT, this.LCT, this.ECT, this.HCT);
        this.setPSR(storage.get(0));
        this.setLSR(storage.get(1));
        this.setESR(storage.get(2));
        this.setHSR(storage.get(3));
         
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
        this.PCT = LCT;
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
    public void setGW13(double GW13) {
        this.GW13 = GW13;
    }
    public double getA71() {
        return A71;
    }
    public void setA71(double A71) {
        this.A71 = A71;
    }
    public double getA8() {
        return A8;
    }
    public void setA8(double A8) {
        this.A8 = A8;
    }

  
}