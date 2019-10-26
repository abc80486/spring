package com.example.demo.chain_data;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.*;


public class data{
    //static double output=4000;//������
    //static double cycleTime = 1.5;//����ʱ�䣻
    //static double storageSum = 0.0;
    //ArrayList<Double> sigalNeed,currentPackQuantity;//��̨����e����ǰ��װ����j
    ArrayList<Double> layer,layerAmount;//������ÿ��������
    public static ArrayList<Double> getUsedAmount(){//R
        //��̨ʹ���� = ����*��̨����/��װ����
        ArrayList<Double> usedAmount = new ArrayList<Double>();
       // usedAmount = getSigalNeed()*getcurrentPackQuantity();
        return usedAmount;
    }

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

    public static void main(String[] args){
       // getStorage(1.5,4000.0);
        System.out.println(getStorage(1.5,4000.0));
    }
    public static List<Double> getStorage(double CT,double output){
        List<Double> storage = new ArrayList<Double>();
        final double RUBBERBOXVOLUME = 0.0756;
    	final int COUNT = 6;
        //ArrayList<Double> lists = new List<Double>();
    	String layerFile = "C:\\Users\\xiangbin\\iCloudDrive\\eclipse\\db_con\\src\\chain_data\\layer.json";
    	String layerAmountFile = "C:\\Users\\xiangbin\\iCloudDrive\\eclipse\\db_con\\src\\chain_data\\layerAmount.json";
    	String sigalNeedFile = "C:\\Users\\xiangbin\\iCloudDrive\\eclipse\\db_con\\src\\chain_data\\sigalNeed.json";
    	String currentPackQuantityFile = "C:\\Users\\xiangbin\\iCloudDrive\\eclipse\\db_con\\src\\chain_data\\currentPackQuantity.json";
    	String demandRubberBoxFile = "C:\\Users\\xiangbin\\iCloudDrive\\eclipse\\db_con\\src\\chain_data\\demandRubberBox.json";
    	String volumeFile = "C:\\Users\\xiangbin\\iCloudDrive\\eclipse\\db_con\\src\\chain_data\\volume.json";


    	JSONArray sigalNeed = JSON.parseArray(fileIn(sigalNeedFile));
        JSONArray currentPackQuantity = JSON.parseArray(fileIn(currentPackQuantityFile));
        JSONArray layer = JSON.parseArray(fileIn(layerFile));
        JSONArray layerAmount = JSON.parseArray(fileIn(layerAmountFile));
        JSONArray demandRubberBox = JSON.parseArray(fileIn(demandRubberBoxFile));
        JSONArray volume = JSON.parseArray(fileIn(volumeFile));
        

        JSONArray requiredBroad = JSON.parseArray("[]");//AE
        JSONArray usedAmount = JSON.parseArray("[]");//R
       
        double  storageAmount = 0.0;
        double packRequiredSum = 0.0;//包材
        double electtonicRequiredSum = 0.0;//电子元器件
        double largestRequiredSum = 0.0;//大件
        double hardwareRequiredSum = 0.0;//五金

        int packN = 13;
        int lagN = 9;
        int elsN = 5;
       
    //    int hdwN = 29;
        //
        //Q
        //计算需求板数量，R列为通用；
         System.out.println(sigalNeed.size());
        for (int i = 0; i < sigalNeed.size(); i++) {
            usedAmount.add((int)Math.ceil(output*sigalNeed.getDoubleValue(i)/currentPackQuantity.getDoubleValue(i)));
            if(demandRubberBox.getDoubleValue(i)!=0) {
                double temp = (int)Math.ceil(volume.getDoubleValue(i)*usedAmount.getDoubleValue(i)/RUBBERBOXVOLUME);
                demandRubberBox.set(i, temp);//AD
                requiredBroad.add(temp/12.0);//需求胶箱数量；
            }else{
                double temp = usedAmount.getDoubleValue(i)/layer.getDoubleValue(i)/layerAmount.getDoubleValue(i);
                requiredBroad.add(temp); 
            }
        }
        //根据板数计算储位，AF
           double temp = 0.0;
            for(int j=0;j<COUNT;j++){
                temp += requiredBroad.getDoubleValue(j);
            }
            packRequiredSum += (int)Math.ceil(temp);//获取第一个值
            //System.out.println(packRequiredSum);
        //for(int i=COUNT;i<pack)
        for (int i = COUNT; i < sigalNeed.size(); i++) {
            //System.out.println(requiredBroad.getDoubleValue(i));
            //不需要胶箱，直接向上取整；
                int temp2 =  demandRubberBox.getIntValue(i);
                double tem=0.0;
                if(temp2==0.0){
                    tem = (int)Math.ceil(requiredBroad.getDoubleValue(i));
                }else if(temp2 == 1){
                    tem = 0.25;
                }else if(temp2 == 2){
                    tem = 0.5;
                }else if(temp2 == 3){
                    tem = 0.75;
                }else if(temp<=12){
                    tem = 1.0;
                }else{
                    tem= (int)Math.ceil(temp2/12.0);
                }
                //System.out.println(tem);    
            if(i<packN){    
                packRequiredSum += tem;
                //System.out.println(packRequiredSum);

            }else if(i<packN+lagN){
                largestRequiredSum += tem;
                System.out.println(tem);
            }else if(i<packN+lagN+elsN){
                electtonicRequiredSum += tem;
            }else{
                hardwareRequiredSum += tem;
            }
        }
        storageAmount = CT*(packRequiredSum+largestRequiredSum+electtonicRequiredSum+hardwareRequiredSum);
        
        storage.add(packRequiredSum*CT);
        storage.add(largestRequiredSum*CT);
        storage.add(electtonicRequiredSum*CT);
        storage.add(hardwareRequiredSum*CT);
        storage.add(storageAmount);
        return storage;
    }

}