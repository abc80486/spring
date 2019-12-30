package com.example.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.jpa.pojo.CarPojo;
import com.example.jpa.service.CarService;

/**
 * @program: SpringBootJPA
 * @description: 车模型控制类
 * @author: LouYue
 * @create: 2019-08-13 16:28
 **/
@RestController
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     * 添加车信息
     *
     * @param pojo 传参模型
     * @return Integer
     */
    @GetMapping(value = "/addCar")
    public CarPojo add( CarPojo pojo,BindingResult result) {
        return carService.add(pojo);
    }

    /**
     * 删除车信息 -> 主键标识
     *
     * @param id 主键标识
     */
    @DeleteMapping(value="/deleteCarById")
    public void deleteById(@RequestParam("id") Integer id){
        carService.deleteById(id);
    }

    /**
     * 修改车信息
     *
     * @param pojo 传参模型
     * @return CarPojo
     */
    @PostMapping(value ="/updateCar")
    public CarPojo update(@RequestBody CarPojo pojo){
        return carService.update(pojo);
    }

    /**
     * 查询车信息列表
     *
     * @return List
     */
    @GetMapping(value = "/selectListCars")
    public List<CarPojo> selectList(){
        return carService.selectList();
    }

    /**
     * 查询车信息列表 - 排序
     *
     * @return List
     */
    @GetMapping(value = "/selectListCarsSort")
    public List<CarPojo>  selectListSorts() {
        return carService.selectListSorts();
    }

    /**
     * 查询车信息 - 主键标识
     *
     * @param id 主键标识
     * @return CarPojo
     */
    @GetMapping(value="/selectCarById")
    public CarPojo selectById(@RequestParam("id") Integer id){
        return carService.selectById(id);
    }
}