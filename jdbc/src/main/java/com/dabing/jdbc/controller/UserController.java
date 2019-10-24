package com.dabing.jdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.beans.factory.annotation;
import java.util.Map;
import java.util.List;
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取示例数据库 t_user 的全部信息 
     * @return 返回 json 数据
     * */
    @GetMapping("/users")
    public Object   getUsers(){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from t_user ");
        return  list;
    }

}