package com.neo.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ServicesCtrl implements Serializable {
    private int id;
    private short operation;
    private short mode;
    private short service;
    private Date time;

    public ServicesCtrl(){
        super();
    }

    public ServicesCtrl(short operation,short mode,short service){
        super();
        this.operation = operation;
        this.mode = mode;
        this.service = service;
        //this.time = new Date();
    }

    
}