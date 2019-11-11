package com.example.demo.TEST.JDBC;

import java.sql.Date;

import javafx.scene.chart.PieChart.Data;

public class User {
    private String user_name;
    private String password;
    private String sax;
    private String last_login_time;

    public String getUser_name(){
        return user_name;
    }
    public void setUser_name(String user_name){
        this.user_name = user_name;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getSax(){
        return sax;
    }
    public void setSax(String sax){
        this.sax = sax;
    }

    public String getLast_login_time(){
        return last_login_time;
    }
    public void setLast_login_time(String last_login_time){
        this.last_login_time = last_login_time;
    }

}