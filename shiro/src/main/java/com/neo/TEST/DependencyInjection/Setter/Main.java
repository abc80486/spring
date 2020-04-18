package com.neo.TEST.DependencyInjection.Setter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String args[]){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:DependencyInjection/Setter/beans.xml");
        Class2 test = (Class2)context.getBean("class2");
        test.method1();


    }
}