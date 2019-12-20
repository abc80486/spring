package com.neo.service;

import org.springframework.beans.factory.annotation.Autowired;

public class a  {
    @Autowired
    public WaterPump1 wp1;

    public static void main(String[] args){
         System.out.println(new a().wp1.getStatus());
    }
    //public void test
 
}