package com.neo.TEST.DependencyInjection.Constructor;

public class Class1 {
    public Class1(){
       System.out.println("进入被引用类构造器" );
    }
    public void method1() {
       System.out.println("进入被引用类方法" );
    } 
 }