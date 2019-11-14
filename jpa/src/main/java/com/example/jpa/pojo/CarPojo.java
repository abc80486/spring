package com.example.jpa.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: SpringBootJPA
 * @description: 车模型实体类
 * @author: LouYue
 * @create: 2019-08-13 16:08
 **/

@Entity(name = "car")
@Table(name="t_car")
public class CarPojo {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @Column
    private String carName;

    @Column
    private String carContent;

    @Column
    private String carType;

    @Column
    private Date createTime;

    @Column
    private Date updateTime;

    public CarPojo(){

    }

    public CarPojo(Integer id, String carName, String carContent, String carType, Date createTime, Date updateTime){
        this.id = id;
        this.carName = carName;
        this.carContent = carContent;
        this.carType = carType;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarContent() {
        return carContent;
    }

    public void setCarContent(String carContent) {
        this.carContent = carContent;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}