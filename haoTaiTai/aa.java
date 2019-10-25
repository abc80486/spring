package chain_data;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import com.alibaba.fastjson.*;
public class aa{
    static double output=8000;//������
    static double cycleTime = 1.5;//����ʱ�䣻
    static double storageSum = 0.0;
    //ArrayList<Double> sigalNeed,currentPackQuantity;//��̨����e����ǰ��װ����j
    ArrayList<Double> layer,layerAmount;//������ÿ��������
    public static ArrayList<Double> getUsedAmount(){//R
        //��̨ʹ���� = ����*��̨����/��װ����
        ArrayList<Double> usedAmount = new ArrayList<Double>();
       // usedAmount = getSigalNeed()*getcurrentPackQuantity();
        return usedAmount;
    }
    public static JSONArray getSigalNeed(){
        JSONArray temp = JSON.parseArray("[ 3, 1, 0.002, 1, 2, 1, 2, 2, 2, 3, 1, 1, 2 ]");
        //ArrayList<Double> temp = [ 3, 1, 0.002, 1, 2, 1, 2, 2, 2, 3, 1, 1, 2 ];
       return temp;
    }
    public static JSONArray getCurrentPackQuantity(){
        JSONArray temp = JSON.parseArray("[35000,2000 ,30,10000,20000,3000 ,500,24,80,200,5,25,25]");
       return temp;
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
    	final int COUNT = 6;
    	final int PACKINGMATERIALS = 13;
    	final int LARGESIZEDOBJECT = 5;
    	final int ELECTRONICDEVICE = 9;
    	final int MINHARDWARE = 29;
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
        
        JSONArray requiredBroad = JSON.parseArray("[]");
        JSONArray usedAmount = JSON.parseArray("[]");
        JSONArray storageAmount = JSON.parseArray("[]");
        
        for (int i = 0; i < PACKINGMATERIALS; i++) {
            usedAmount.add((int)Math.ceil(output*sigalNeed.getDoubleValue(i)/currentPackQuantity.getDoubleValue(i)));
        }
        for (int i = 0; i < PACKINGMATERIALS; i++) {
             //System.out.print(i+"---");
        	requiredBroad.add(usedAmount.getDoubleValue(i)/layer.getDoubleValue(i)/layerAmount.getDoubleValue(i));
        	//System.out.println(requiredBroad.getDoubleValue(i));
        }
        double tem=0.0;
        for (int i = 0; i < COUNT; i++) {
        	tem += requiredBroad.getDoubleValue(i);
        }
        storageAmount.add((int)Math.ceil(tem));
        
        for (int i = 0; i < demandRubberBox.size(); i++) {
        	if(demandRubberBox.getDoubleValue(i)!=0) {
               	double temp = volume.getDoubleValue(i)*usedAmount.getDoubleValue(i);
        		demandRubberBox.set(i, temp);
        	}
        }
        for (int i = COUNT; i < PACKINGMATERIALS; i++) {
        	if(demandRubberBox.getDoubleValue(i)==0) {
        		storageAmount.add((int)Math.ceil(requiredBroad.getDoubleValue(i)));
        	}
        	else {
         	}
        }
        for (int i = 0; i < storageAmount.size(); i++) {
        	storageSum += storageAmount.getDoubleValue(i);
        	System.out.println(storageAmount.getDoubleValue(i));
        }
        storageSum = storageSum * cycleTime;
    	System.out.println(storageSum);
    }


}