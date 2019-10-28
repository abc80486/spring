package com.example.demo.chain_data;

import java.io.FileInputStream;
import java.io.InputStream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class getSData {
    public static JSONArray sigalNeed = new JSONArray() ;;
    public static JSONArray currentPackQuantity = new JSONArray() ;;
    public static JSONArray layer = new JSONArray() ;
    public static JSONArray layerAmount = new JSONArray() ;
    public static JSONArray demandRubberBox = new JSONArray() ;
    public static JSONArray volume =new JSONArray() ;
    
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
    public static boolean get(String fileAddress){
        String dataFile = "C:\\Users\\xiangbin\\iCloudDrive\\code\\java\\SPRING\\demo\\src\\main\\java\\com\\example\\demo\\chain_data\\";
        dataFile += fileAddress; 
        JSONArray sigal = JSON.parseArray(fileIn(dataFile));
        int size = sigal.size();
       // System.out.println(size);
        for(int i=0;i<size;i++){
            JSONArray rol = new JSONArray() ;
            rol = sigal.getJSONArray(i);
            sigalNeed.add(rol.getDoubleValue(0));
            currentPackQuantity.add(rol.getDoubleValue(1));
            volume.add(rol.getDoubleValue(2));
            layer.add(rol.getDoubleValue(3));
            layerAmount.add(rol.getDoubleValue(4));
            demandRubberBox.add(rol.getDoubleValue(5));
        }
        return true;
    }
    /*
    public static void main(String[] args){
        getSData.get("gw12.json");
        System.out.println(getSData.sigalNeed);
    }
    */

}