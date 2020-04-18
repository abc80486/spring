package com.neo.TEST.DependencyInjection.Constructor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class Main {
   public static void main(String[] args) {
      ApplicationContext context = 
             new ClassPathXmlApplicationContext("classpath:DependencyInjection/Constructor/beans.xml");
      Class2 te = (Class2) context.getBean("class2");
      te.method1();
   }
}