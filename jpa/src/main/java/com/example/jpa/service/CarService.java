package com.example.jpa.service;

import java.util.List;

import com.example.jpa.pojo.CarPojo;

/**
 * @program: SpringBootJPA
 * @description: 车模型业务接口类
 * @author: LouYue
 * @create: 2019-08-13 16:13
 **/
public interface CarService {

    /**
     * 添加车信息
     *
     * @param pojo 传参模型
     * @return Integer
     */
    CarPojo add(CarPojo pojo);

    /**
     * 删除车信息 -> 主键标识
     *
     * @param id 主键标识
     */
    void deleteById(Integer id);

    /**
     * 修改车信息
     *
     * @param pojo 传参模型
     * @return CarPojo
     */
    CarPojo update(CarPojo pojo);

    /**
     * 查询车信息列表
     *
     * @return List
     */
    List<CarPojo> selectList();

    /**
     * 查询车信息列表 - 排序
     *
     * @return List
     */
    List<CarPojo> selectListSorts();

    /**
     * 查询车信息 - 主键标识
     *
     * @param id 主键标识
     * @return CarPojo
     */
    CarPojo selectById(Integer id);
}