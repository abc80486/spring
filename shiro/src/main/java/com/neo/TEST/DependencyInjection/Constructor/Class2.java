package com.neo.TEST.DependencyInjection.Constructor;

public class Class2 {
    private Class1 class1;
    
    public Class2(Class1 class1) {
       System.out.println("引用类的构造器");
       this.class1 = class1;
    }
    
    public void method1() {
       class1.method1();
    }
 }