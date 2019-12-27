package com.neo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class EquipmentStatus implements Serializable{

    public EquipmentStatus(List<Object> data){

        this.wp1_ctrl = (boolean)data.get(0);
        this.wp1_status = (boolean)data.get(1);
        this.wp1_mode = (boolean)data.get(2);

        this.wp2_ctrl = (boolean)data.get(3);
        this.wp2_status = (boolean)data.get(4);
        this.wp2_mode = (boolean)data.get(5);

        this.wp3_ctrl = (boolean)data.get(6);
        this.wp3_status = (boolean)data.get(7);
        this.wp3_mode = (boolean)data.get(8);

        this.valve1_ctrl = (boolean)data.get(9);
        this.valve1_open_status = (boolean)data.get(10);
        this.valve1_close_status = (boolean)data.get(11);
        this.valve1_mode = (boolean)data.get(12);

        this.valve2_ctrl = (boolean)data.get(13);
        this.valve2_open_status = (boolean)data.get(14);
        this.valve2_close_status = (boolean)data.get(15);
        this.valve2_mode = (boolean)data.get(16);

        this.time = (Date)data.get(17);

    }
    EquipmentStatus(){}
    private int id;
    private Date time;

    private boolean wp1_ctrl;
    private boolean wp1_status;
    private boolean wp1_mode;

    private boolean wp2_ctrl;
    private boolean wp2_status;
    private boolean wp2_mode;

    private boolean wp3_ctrl;
    private boolean wp3_status;
    private boolean wp3_mode;

    private boolean valve1_ctrl;
    private boolean valve1_open_status;
    private boolean valve1_close_status;
    private boolean valve1_mode;

    private boolean valve2_ctrl;
    private boolean valve2_open_status;
    private boolean valve2_close_status;
    private boolean valve2_mode;

}