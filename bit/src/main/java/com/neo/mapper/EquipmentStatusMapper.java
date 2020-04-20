package com.neo.mapper;

import java.util.List;

import com.neo.model.EquipmentStatus;
import com.neo.web.CtrlDataSave;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface EquipmentStatusMapper {

    @Select("SELECT id,time,wp1_ctrl,wp1_status,wp1_mode,wp2_ctrl,wp2_status,wp2_mode,wp3_ctrl,wp3_status,wp3_mode,valve1_ctrl,valve1_status,valve1_mode ,valve2_ctrl,valve2_status,valve2_mode FROM MAIN_DATA WHERE  time>=#{sdate} && time<=#{edate}")
    public List<EquipmentStatus> getByTime(@Param("sdate") String sdate,@Param("edate") String edate);
    
    public default EquipmentStatus getLast() {
        return new EquipmentStatus(CtrlDataSave.dataLast);
    }
}