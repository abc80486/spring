package com.example.jpa;

import com.example.jpa.service.CarService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


//测试类
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ClientuseApplicationTests {

    @Autowired
    static CarService carService;
    //@Test
    public static void main(String[] args) {
         //CarService carService;
         System.out.println(carService.selectList());
    }

}
