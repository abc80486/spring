package com.example.demo.chain_data;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.*;

public class Data{
     public static void main(String[] args){
        // data a = new data();
        //System.out.println(getSRgw11(4000,2,3,7.0,7.0));
        //System.out.println(getSRa71(2400.0,7.0,7.0,7.0,7.0));

     }
//*/
    public  List<Double> getSRgw11(double output,double PCT,double LCT,double ECT,double HCT){
        List<Double> storage = new ArrayList<Double>();
        double RUBBERBOXVOLUME = 0.0756;
    	int COUNT = 6;
        JSONArray requiredBroad = JSON.parseArray("[]");//AE
        JSONArray usedAmount = JSON.parseArray("[]");//R
        
        if(GetSData.get("gw11.json")==false) return null;
        double  storageAmount = 0.0;
        double packRequiredSum = 0.0;//
        double electtonicRequiredSum = 0.0;//
        double largestRequiredSum = 0.0;//
        double hardwareRequiredSum = 0.0;//

        for (int i = 0; i < GetSData.sigalNeed.size(); i++) {
            usedAmount.add((int)Math.ceil(output*GetSData.sigalNeed.getDoubleValue(i)/GetSData.currentPackQuantity.getDoubleValue(i)));
            if(GetSData.demandRubberBox.getDoubleValue(i)!=0) {
                double temp = (int)Math.ceil(GetSData.volume.getDoubleValue(i)*usedAmount.getDoubleValue(i)/RUBBERBOXVOLUME);
                GetSData.demandRubberBox.set(i, temp);//AD
                requiredBroad.add(temp/12.0);//
            }else{
                double temp = usedAmount.getDoubleValue(i)/GetSData.layer.getDoubleValue(i)/GetSData.layerAmount.getDoubleValue(i);
                requiredBroad.add(temp); 
            }
        }
        double temp = 0.0;
        for(int j=0;j<COUNT;j++){
            temp += requiredBroad.getDoubleValue(j);
        }
        packRequiredSum += (int)Math.ceil(temp);
        int num = GetSData.sigalNeed.size();
        for (int i = COUNT; i < num; i++) {
                int temp2 =  GetSData.demandRubberBox.getIntValue(i);
                double tem=0.0;
                if(temp2==0.0){
                    tem = (int)Math.ceil(requiredBroad.getDoubleValue(i));

                }else if(temp2 <= 3){
                    tem = temp2 * 0.25;
                }else if(temp2<=12){
                    tem = 1.0;
                }else{
                    tem= (int)Math.ceil(temp2/12.0);
                }
                //System.out.println(tem);  
                int ctgy = GetSData.category.getIntValue(i);  
            if(ctgy==11){    
                packRequiredSum += tem;
                //System.out.println(packRequiredSum);

            }else if(ctgy==12){
                largestRequiredSum += tem;
              // System.out.println(tem);
            }else if(ctgy==13){
                electtonicRequiredSum += tem;
               // System.out.println(tem);
            }else if(ctgy==14){
                hardwareRequiredSum += tem;
                //System.out.println(tem);
            }
        }        
        storage.add(packRequiredSum*PCT);
        storage.add(largestRequiredSum*LCT);
        storage.add(electtonicRequiredSum*ECT);
        storage.add(hardwareRequiredSum*HCT);

        storageAmount = packRequiredSum*PCT + largestRequiredSum*LCT + electtonicRequiredSum*ECT + hardwareRequiredSum*HCT;
        storage.add(storageAmount);
        return storage;
    }
    public  List<Double> getSRgw12(double output,double PCT,double LCT,double ECT,double HCT){
        List<Double> storage = new ArrayList<Double>();
        double RUBBERBOXVOLUME = 0.0756;
        int COUNT = 7;
        //ArrayList<Double> lists = new List<Double>();
        JSONArray requiredBroad = JSON.parseArray("[]");//AE
        JSONArray usedAmount = JSON.parseArray("[]");//R
       
        if(GetSData.get("gw12.json")==false) return null;
        double  storageAmount = 0.0;
        double packRequiredSum = 0.0;//
        double electtonicRequiredSum = 0.0;//
        double largestRequiredSum = 0.0;//
        double hardwareRequiredSum = 0.0;//
        List<Double> packRequired = new ArrayList<Double>();
        List<Double> largestRequired = new ArrayList<Double>();
        List<Double> electtonicRequired = new ArrayList<Double>();
        List<Double> hardwareRequired = new ArrayList<Double>();

       
    //    int hdwN = 29;
        //
        //Q
        //System.out.println(getSData.sigalNeed.size());
        for (int i = 0; i < GetSData.sigalNeed.size(); i++) {
            usedAmount.add((int)Math.ceil(output*GetSData.sigalNeed.getDoubleValue(i)/GetSData.currentPackQuantity.getDoubleValue(i)));
            if(GetSData.demandRubberBox.getDoubleValue(i)!=0) {
                double temp = (int)Math.ceil(GetSData.volume.getDoubleValue(i)*usedAmount.getDoubleValue(i)/RUBBERBOXVOLUME);
                GetSData.demandRubberBox.set(i, temp);//AD
                requiredBroad.add(temp/12.0);//
            }else{
                double temp = usedAmount.getDoubleValue(i)/GetSData.layer.getDoubleValue(i)/GetSData.layerAmount.getDoubleValue(i);
                requiredBroad.add(temp); 
            }
        }
           double temp = 0.0;
            for(int j=0;j<COUNT;j++){
                temp += requiredBroad.getDoubleValue(j);
            }
            packRequiredSum += (int)Math.ceil(temp);//
            //System.out.println(packRequiredSum);
        //for(int i=COUNT;i<pack)
        int num = GetSData.sigalNeed.size();
        for (int i = 0; i < num; i++){
            //System.out.println(requiredBroad.getDoubleValue(i));
        }
        double ex = 0.0;
        for (int i = COUNT; i < num; i++) {
                int temp2 =  GetSData.demandRubberBox.getIntValue(i);
                double tem=0.0;
                if(temp2==0.0){
                    tem = (int)Math.ceil(requiredBroad.getDoubleValue(i));

                }else if(temp2 <= 3){
                    tem = temp2 * 0.25;
                }else if(temp<=12){
                    tem = 1.0;
                }else{
                    tem= (int)Math.ceil(temp2/12.0);
                }
                //System.out.println(tem);  
                int ctgy = GetSData.category.getIntValue(i);  
            if(ctgy==21){    
                packRequiredSum += tem;
                //System.out.println(packRequiredSum);
                packRequired.add(tem);

            }else if(ctgy==22){
                largestRequiredSum += tem;
              // System.out.println(tem);
                largestRequired.add(tem);
            }else if(ctgy==23){
                electtonicRequiredSum += tem;
               // System.out.println(tem);
                electtonicRequired.add(tem);
            }else if(ctgy==24){
                hardwareRequiredSum += tem;
                hardwareRequired.add(tem);
                //System.out.println(tem);
            }else if(ctgy==223){
                ex = tem * ECT;
            }
        }        
        storage.add(packRequiredSum*PCT);
        storage.add(largestRequiredSum*LCT+ex);
        storage.add(electtonicRequiredSum*ECT);
        storage.add(hardwareRequiredSum*HCT);

        storageAmount = packRequiredSum*PCT + largestRequiredSum*LCT+ex + electtonicRequiredSum*ECT + hardwareRequiredSum*HCT;
        storage.add(storageAmount);
        return storage;
    }
    
    //public final static int num13 = getSData.sigalNeed.size();
    public   List<Double> getSRgw13(double output,double PCT,double LCT,double ECT,double HCT){
        List<Double> storage = new ArrayList<Double>();
        double RUBBERBOXVOLUME = 0.0756;
    	int COUNT = 5;
        //ArrayList<Double> lists = new List<Double>();
        JSONArray requiredBroad = JSON.parseArray("[]");//AE
        JSONArray usedAmount = JSON.parseArray("[]");//R
       
        if(GetSData.get("gw13.json")==false) return null;
        double  storageAmount = 0.0;
        double packRequiredSum = 0.0;//
        double electtonicRequiredSum = 0.0;//
        double largestRequiredSum = 0.0;//
        double hardwareRequiredSum = 0.0;//

        final int num = GetSData.sigalNeed.size();
       
    //    int hdwN = 29;
        //
        //Q
       // System.out.println(getSData.sigalNeed.size());
        for (int i = 0; i < num; i++) {
            usedAmount.add((int)Math.ceil(output*GetSData.sigalNeed.getDoubleValue(i)/GetSData.currentPackQuantity.getDoubleValue(i)));
            if(GetSData.demandRubberBox.getDoubleValue(i)!=0) {
                double temp = (int)Math.ceil(GetSData.volume.getDoubleValue(i)*usedAmount.getDoubleValue(i)/RUBBERBOXVOLUME);
                GetSData.demandRubberBox.set(i, temp);//AD
                requiredBroad.add(temp/12.0);//
            }else{
                double temp = usedAmount.getDoubleValue(i)/GetSData.layer.getDoubleValue(i)/GetSData.layerAmount.getDoubleValue(i);
                requiredBroad.add(temp); 
            }
        }
           double tempf = 0.0;
            for(int j=0;j<COUNT;j++){
                tempf += requiredBroad.getDoubleValue(j);
            }
            packRequiredSum += (int)Math.ceil(tempf);//
         //System.out.println(num13);
        //for(int i=COUNT;i<pack)
        //int num = getSData.sigalNeed.size();
        for (int i = COUNT; i <num; i++) {
                int temp =  GetSData.demandRubberBox.getIntValue(i);
                double tem=0.0;
                if(temp==0.0){
                    tem = (int)Math.ceil(requiredBroad.getDoubleValue(i));

                }else if(temp <= 3){
                    tem = temp * 0.25;
                }else if(temp<=12){
                    tem = 1.0;
                }else if(temp>12){
                    tem= (int)Math.ceil(temp/12.0);
                   // System.out.println(tem);
                }
                //System.out.println(tem);    
            int ctgy = GetSData.category.getIntValue(i);

            if(ctgy==31){    
                packRequiredSum += tem;
                //System.out.println(tem);
            }else if(ctgy==32){
                largestRequiredSum += tem;
              // System.out.println(tem);
               // largestRequired.add(tem);
            }else if(ctgy==33){
                electtonicRequiredSum += tem;
               // System.out.println(tem);
            }else if(ctgy==34){
                hardwareRequiredSum += tem;
                //System.out.println(tem);
            }
        }        
       // System.out.println(packRequiredSum);
       //requiredBroad.clear();
        storage.add(packRequiredSum*PCT);
        storage.add(largestRequiredSum*LCT);
        storage.add(electtonicRequiredSum*ECT);
        storage.add(hardwareRequiredSum*HCT);
        storageAmount = packRequiredSum*PCT + largestRequiredSum*LCT + electtonicRequiredSum*ECT + hardwareRequiredSum*HCT;
        storage.add(storageAmount);
        return storage;
    }
    public   List<Double> getSRa71(double output,double PCT,double LCT,double ECT,double HCT){
        List<Double> storage = new ArrayList<Double>();
        double RUBBERBOXVOLUME = 0.0756;
    	int COUNT = 4;
        //ArrayList<Double> lists = new List<Double>();
        JSONArray requiredBroad = JSON.parseArray("[]");//AE
        JSONArray usedAmount = JSON.parseArray("[]");//R
       
        if(GetSData.get("a71.json")==false) return null;
        double  storageAmount = 0.0;
        double packRequiredSum = 0.0;//
        double electtonicRequiredSum = 0.0;//
        double largestRequiredSum = 0.0;//
        double hardwareRequiredSum = 0.0;//

        final int num = GetSData.sigalNeed.size();
       
    //    int hdwN = 29;
        //
        //Q
       // System.out.println(getSData.sigalNeed.size());
        for (int i = 0; i < num; i++) {
            usedAmount.add((int)Math.ceil(output*GetSData.sigalNeed.getDoubleValue(i)/GetSData.currentPackQuantity.getDoubleValue(i)));
            if(GetSData.demandRubberBox.getDoubleValue(i)!=0) {
                double temp = (int)Math.ceil(GetSData.volume.getDoubleValue(i)*usedAmount.getDoubleValue(i)/RUBBERBOXVOLUME);
                GetSData.demandRubberBox.set(i, temp);//AD
                requiredBroad.add(temp/12.0);//
            }else{
                double temp = usedAmount.getDoubleValue(i)/GetSData.layer.getDoubleValue(i)/GetSData.layerAmount.getDoubleValue(i);
                requiredBroad.add(temp); 
            }
        }
           double tempf = 0.0;
            for(int j=0;j<COUNT;j++){
                tempf += requiredBroad.getDoubleValue(j);
            }
            packRequiredSum += (int)Math.ceil(tempf);//
        // System.out.println(tempf);
        //for(int i=COUNT;i<pack)
        //int num = getSData.sigalNeed.size();
        for (int i = COUNT; i <num; i++) {
                int temp =  GetSData.demandRubberBox.getIntValue(i);
                double tem=0.0;
                if(temp==0.0){
                    tem = (int)Math.ceil(requiredBroad.getDoubleValue(i));

                }else if(temp <= 3){
                    tem = temp * 0.25;
                }else if(temp<=12){
                    tem = 1.0;
                }else if(temp>12){
                    tem= (int)Math.ceil(temp/12.0);
                   // System.out.println(tem);
                }
                //System.out.println(tem);    
            int ctgy = GetSData.category.getIntValue(i);

            if(ctgy==41){    

                packRequiredSum += tem;
               // System.out.println(tem);
            }else if(ctgy==42){
                largestRequiredSum += tem;
               //System.out.println(tem);
               // largestRequired.add(tem);
            }else if(ctgy==43){
                electtonicRequiredSum += tem;
               // System.out.println(tem);
            }else if(ctgy==44){
                hardwareRequiredSum += tem;
                //System.out.println(tem);
            }
        }        
       // System.out.println(packRequiredSum);
       //requiredBroad.clear();
        storage.add(packRequiredSum*PCT);
        storage.add(largestRequiredSum*LCT);
        storage.add(electtonicRequiredSum*ECT);
        storage.add(hardwareRequiredSum*HCT);
        storageAmount = packRequiredSum*PCT + largestRequiredSum*LCT + electtonicRequiredSum*ECT + hardwareRequiredSum*HCT;
        storage.add(storageAmount);
        return storage;
    }
    public   List<Double> getSRv8(double output,double PCT,double LCT,double ECT,double HCT){
        List<Double> storage = new ArrayList<Double>();
        double RUBBERBOXVOLUME = 0.0756;
    	int COUNT = 0;
        //ArrayList<Double> lists = new List<Double>();
        JSONArray requiredBroad = JSON.parseArray("[]");//AE
        JSONArray usedAmount = JSON.parseArray("[]");//R
       
        if(GetSData.get("v8.json")==false) return null;
        double  storageAmount = 0.0;
        double packRequiredSum = 0.0;//
        double electtonicRequiredSum = 0.0;//
        double largestRequiredSum = 0.0;//
        double hardwareRequiredSum = 0.0;//

        final int num = GetSData.sigalNeed.size();
       
    //    int hdwN = 29;
        //
        //Q
       // System.out.println(getSData.sigalNeed.size());
        for (int i = 0; i < num; i++) {
            usedAmount.add((int)Math.ceil(output*GetSData.sigalNeed.getDoubleValue(i)/GetSData.currentPackQuantity.getDoubleValue(i)));
            if(GetSData.demandRubberBox.getDoubleValue(i)!=0) {
                double temp = (int)Math.ceil(GetSData.volume.getDoubleValue(i)*usedAmount.getDoubleValue(i)/RUBBERBOXVOLUME);
                GetSData.demandRubberBox.set(i, temp);//AD
                requiredBroad.add(temp/12.0);//
            }else{
                double temp = usedAmount.getDoubleValue(i)/GetSData.layer.getDoubleValue(i)/GetSData.layerAmount.getDoubleValue(i);
                requiredBroad.add(temp); 
            }
        }
           double tempf = 0.0;
            for(int j=0;j<COUNT;j++){
                tempf += requiredBroad.getDoubleValue(j);
            }
            packRequiredSum += (int)Math.ceil(tempf);//
         //System.out.println(num13);
        //for(int i=COUNT;i<pack)
        //int num = getSData.sigalNeed.size();
        for (int i = COUNT; i <num; i++) {
                int temp =  GetSData.demandRubberBox.getIntValue(i);
                double tem=0.0;
                if(temp==0.0){
                    tem = (int)Math.ceil(requiredBroad.getDoubleValue(i));

                }else if(temp <= 3){
                    tem = temp * 0.25;
                }else if(temp<=12){
                    tem = 1.0;
                }else if(temp>12){
                    tem= (int)Math.ceil(temp/12.0);
                   // System.out.println(tem);
                }
                //System.out.println(tem);    
            int ctgy = GetSData.category.getIntValue(i);

            if(ctgy==51){    

                packRequiredSum += tem;
                //System.out.println(tem);
            }else if(ctgy==52){
                largestRequiredSum += tem;
               //System.out.println(tem);
               // largestRequired.add(tem);
            }else if(ctgy==53){
                electtonicRequiredSum += tem;
               // System.out.println(tem);
            }else if(ctgy==54){
                hardwareRequiredSum += tem;
                //System.out.println(tem);
            }
        }        
       // System.out.println(packRequiredSum);
       //requiredBroad.clear();
        storage.add(packRequiredSum*PCT);
        storage.add(largestRequiredSum*LCT);
        storage.add(electtonicRequiredSum*ECT);
        storage.add(hardwareRequiredSum*HCT);
        storageAmount = packRequiredSum*PCT + largestRequiredSum*LCT + electtonicRequiredSum*ECT + hardwareRequiredSum*HCT;
        storage.add(storageAmount);
        return storage;
    }
}
