package com.neo.TEST.BeanPostProcessor;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class Main {
   public static void main(String[] args) {
      AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:BeanPostProcessor/beans.xml");
      Message obj = (Message) context.getBean("message");
      obj.getMessage();
      context.registerShutdownHook();
   }
}