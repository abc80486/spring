package com.example.demo.chain_data;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class data{
    public static String fileIn(String strFile) {
      	String end = "";
        try{
            InputStream is = new FileInputStream(strFile);
            int iAvail = is.available();
            byte[] bytes = new byte[iAvail];
            is.read(bytes);
            end +=new String(bytes);
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return end;
    }
/*
    public static void main(String[] args){
       // getStorage(1.5,4000.0);
        System.out.println(getStorage(1.5,3,7,7,4000.0));
    }
    */
    @RequestMapping("/getStorage")
    public static List<Double> getda(double OUTPUT,double PCT,double LCT,double ECT,double HCT){
        // getStorage(1.5,4000.0);
        //List<Double> storage = new ArrayList<Double>();
        //return getStorage(1.5,3,7,7,4000.0);
        return getStorage(OUTPUT,PCT,LCT,ECT,HCT);
     }
 
     public static void main(String[] args){
        System.out.println(getSRgw12(3200,2,3,7,7));
     }

    public static List<Double> getStorage(double output,double PCT,double LCT,double ECT,double HCT){
        List<Double> storage = new ArrayList<Double>();
        final double RUBBERBOXVOLUME = 0.0756;
    	final int COUNT = 6;
        //ArrayList<Double> lists = new List<Double>();
        String pFile = "C:\\Users\\xiangbin\\iCloudDrive\\code\\java\\SPRING\\demo\\src\\main\\java\\com\\example\\demo\\chain_data\\";
    	String layerFile = pFile+"layer.json";
    	String layerAmountFile = pFile+"layerAmount.json";
    	String sigalNeedFile = pFile+"sigalNeed.json";
    	String currentPackQuantityFile = pFile+"currentPackQuantity.json";
    	String demandRubberBoxFile = pFile+"demandRubberBox.json";
    	String volumeFile = pFile+"volume.json";


    	JSONArray sigalNeed = JSON.parseArray(fileIn(sigalNeedFile));
        JSONArray currentPackQuantity = JSON.parseArray(fileIn(currentPackQuantityFile));
        JSONArray layer = JSON.parseArray(fileIn(layerFile));
        JSONArray layerAmount = JSON.parseArray(fileIn(layerAmountFile));
        JSONArray demandRubberBox = JSON.parseArray(fileIn(demandRubberBoxFile));
        JSONArray volume = JSON.parseArray(fileIn(volumeFile));
        

        JSONArray requiredBroad = JSON.parseArray("[]");//AE
        JSONArray usedAmount = JSON.parseArray("[]");//R
       
        double  storageAmount = 0.0;
        double packRequiredSum = 0.0;//鍖呮潗
        double electtonicRequiredSum = 0.0;//鐢靛瓙鍏冨櫒浠�
        double largestRequiredSum = 0.0;//澶т欢
        double hardwareRequiredSum = 0.0;//浜旈噾

        List<Double> packRequired = new ArrayList<Double>();
        List<Double> largestRequired = new ArrayList<Double>();
        List<Double> electtonicRequired = new ArrayList<Double>();
        List<Double> hardwareRequired = new ArrayList<Double>();

        int packN = 13;
        int lagN = 5;
        int elsN = 9;
       
    //    int hdwN = 29;
        //
        //Q
        //璁＄畻闇�姹傛澘鏁伴噺锛孯鍒椾负閫氱敤锛�
         System.out.println(sigalNeed.size());
        for (int i = 0; i < sigalNeed.size(); i++) {
            usedAmount.add((int)Math.ceil(output*sigalNeed.getDoubleValue(i)/currentPackQuantity.getDoubleValue(i)));
            if(demandRubberBox.getDoubleValue(i)!=0) {
                double temp = (int)Math.ceil(volume.getDoubleValue(i)*usedAmount.getDoubleValue(i)/RUBBERBOXVOLUME);
                demandRubberBox.set(i, temp);//AD
                requiredBroad.add(temp/12.0);//闇�姹傝兌绠辨暟閲忥紱
            }else{
                double temp = usedAmount.getDoubleValue(i)/layer.getDoubleValue(i)/layerAmount.getDoubleValue(i);
                requiredBroad.add(temp); 
            }
        }
        //鏍规嵁鏉挎暟璁＄畻鍌ㄤ綅锛孉F
           double temp = 0.0;
            for(int j=0;j<COUNT;j++){
                temp += requiredBroad.getDoubleValue(j);
            }
            packRequiredSum += (int)Math.ceil(temp);//鑾峰彇绗竴涓��
            //System.out.println(packRequiredSum);
        //for(int i=COUNT;i<pack)
        int num = sigalNeed.size();
        for (int i = COUNT; i < num; i++) {
            //System.out.println(requiredBroad.getDoubleValue(i));
            //涓嶉渶瑕佽兌绠憋紝鐩存帴鍚戜笂鍙栨暣锛�
                int temp2 =  demandRubberBox.getIntValue(i);
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
            if(i<packN){    
                packRequiredSum += tem;
                //System.out.println(packRequiredSum);
                packRequired.add(tem);

            }else if(i<packN+lagN){
                largestRequiredSum += tem;
              // System.out.println(tem);
                largestRequired.add(tem);
            }else if(i<packN+lagN+elsN){
                electtonicRequiredSum += tem;
               // System.out.println(tem);
                electtonicRequired.add(tem);
            }else {
                hardwareRequiredSum += tem;
                hardwareRequired.add(tem);
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
    public static List<Double> getSRgw12(double output,double PCT,double LCT,double ECT,double HCT){
        List<Double> storage = new ArrayList<Double>();
        final double RUBBERBOXVOLUME = 0.08;
    	final int COUNT = 7;
        //ArrayList<Double> lists = new List<Double>();
        JSONArray requiredBroad = JSON.parseArray("[]");//AE
        JSONArray usedAmount = JSON.parseArray("[]");//R
       
        if(getSData.get("gw12.json")==false) return null;
        double  storageAmount = 0.0;
        double packRequiredSum = 0.0;//
        double electtonicRequiredSum = 0.0;//
        double largestRequiredSum = 0.0;//
        double hardwareRequiredSum = 0.0;//
        int packN = 14;
        int lagN = 9;
        int elsN = 12;


        List<Double> packRequired = new ArrayList<Double>();
        List<Double> largestRequired = new ArrayList<Double>();
        List<Double> electtonicRequired = new ArrayList<Double>();
        List<Double> hardwareRequired = new ArrayList<Double>();

       
    //    int hdwN = 29;
        //
        //Q
        //System.out.println(getSData.sigalNeed.size());
        for (int i = 0; i < getSData.sigalNeed.size(); i++) {
            usedAmount.add((int)Math.ceil(output*getSData.sigalNeed.getDoubleValue(i)/getSData.currentPackQuantity.getDoubleValue(i)));
            if(getSData.demandRubberBox.getDoubleValue(i)!=0) {
                double temp = (int)Math.ceil(getSData.volume.getDoubleValue(i)*usedAmount.getDoubleValue(i)/RUBBERBOXVOLUME);
                getSData.demandRubberBox.set(i, temp);//AD
                requiredBroad.add(temp/12.0);//
            }else{
                double temp = usedAmount.getDoubleValue(i)/getSData.layer.getDoubleValue(i)/getSData.layerAmount.getDoubleValue(i);
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
        int num = getSData.sigalNeed.size();
        for (int i = 0; i < num; i++){
            System.out.println(requiredBroad.getDoubleValue(i));
        }
        for (int i = COUNT; i < num; i++) {
                int temp2 =  getSData.demandRubberBox.getIntValue(i);
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
            if(i<packN){    
                packRequiredSum += tem;
                //System.out.println(packRequiredSum);
                packRequired.add(tem);

            }else if(i<packN+lagN){
                largestRequiredSum += tem;
              // System.out.println(tem);
                largestRequired.add(tem);
            }else if(i<packN+lagN+elsN){
                electtonicRequiredSum += tem;
               // System.out.println(tem);
                electtonicRequired.add(tem);
            }else {
                hardwareRequiredSum += tem;
                hardwareRequired.add(tem);
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


}