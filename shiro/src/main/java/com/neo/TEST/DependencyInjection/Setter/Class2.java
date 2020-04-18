package com.neo.TEST.DependencyInjection.Setter;

public class Class2{
    Class1 class1;
    public void setclass1(Class1 class1){
        this.class1 = class1;
    }
    public void method1(){
        class1.method1();
    }
}