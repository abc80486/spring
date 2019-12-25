package com.neo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test{
    public static void main(String[] args){
        Map<String,Integer> a = new HashMap<>();
        List<Integer> b = new ArrayList<>();
        long t = new Date().getTime();
        for(int i=0;i<100000;i++){
            a.put("a"+i, i);
            //b.add(i);
        }
        a.get("a344v");
        //a.get("a3440");
        System.out.println(new Date().getTime()-t);
        //a.put("hello", 29);
        //
        //a.remove("hello");
    }
    
}