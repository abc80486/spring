package com.example.demo;

import java.util.ArrayList;
import com.alibaba.fastjson.*;
public class data {
    double output=4000;//������
    double cycleTime[] = {2,3,7,7};//����ʱ�䣻
    ArrayList<Double> sigalNeed,currentPackQuantity;//��̨����e����ǰ��װ����j
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
    public static JSONArray getcurrentPackQuantity(){
        
        JSONArray temp = JSON.parseArray("[35000,2000 ,30,10000,20000,3000 ,500,24,80,200,5,25,25]");
       return temp;
    }
    public static void main(String[] args){
        //ArrayList<Double> lists = new List<Double>();
        JSONArray sigalNeed = JSON.parseArray("[]");
        JSONArray currentPackQuantity = JSON.parseArray("[]");
        JSONArray usedAmount = JSON.parseArray("[]");
        for (int i = 0; i < sigalNeed.size(); i++) {
            // System.out.println(temp.get(i));
          //  sigalNe
        }
    }


}