package com.neo.TEST.parent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
   public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext("classpath:parent/beans.xml");

     // MessageBase objA = (MessageBase) context.getBean("message_base");

     // objA.getMessage1();
     // objA.getMessage2();

     
      MessageParent objB = (MessageParent) context.getBean("message_parent");
      objB.getMessage1();
      objB.getMessage2();
      objB.getMessage3();
   }
}