package com.example.jpa.respository;

import com.example.jpa.pojo.CarPojo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: SpringBootJPA
 * @description: 车模型数据交互接口类 - JPA Repository
 * @author: LouYue
 * @create: 2019-08-13 16:07
 **/
@Repository
public interface CarRepository extends JpaRepository<CarPojo,Integer> {

    /**
     * 根据主键查询信息
     *
     * @param id 主键ID
     * @return CarPojo
     */
    CarPojo getById(Integer id);
}