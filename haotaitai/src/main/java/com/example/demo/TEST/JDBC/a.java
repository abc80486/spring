package com.example.demo.TEST.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jdbc")
public class a {
    @Resource
    private JdbcTemplate jdbcTemplate;
    
    @RequestMapping("/userlist")
    public String getUserList(ModelMap map){
        String sql = "SELECT * FROM t_user";
        List<User> userList = jdbcTemplate.query(sql, new RowMapper<User>() {
            User user = null;
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                user = new User();
                user.setUser_name(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setSax(rs.getString("sex"));
                user.setLast_login_time(rs.getString("last_login_time"));
                return user;
        }});
        for(User user:userList){
            System.out.println(user.getUser_name());
        }
        map.addAttribute("users", userList);
        return "test/user";
    }
        
       
    }

