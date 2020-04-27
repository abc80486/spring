package com.neo.mapper;

import com.neo.model.CallBack;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface CallBackMapper {
    //获取最新的数据，根据周期，k值，n值获取
    @Select("SELECT * FROM CallBack_Rate WHERE cycle=${T} AND k=${k} AND n=${n}")
    CallBack getDBLatelyData(int T,int k,int n);

    //cun4=${cun4},sum4=${sum4},p4=${p4}

    //更新行数据
    @Update("UPDATE CallBack_Rate SET "
    +"latelyTime=${latelyTime},"
    +"cun2=${cun2},sum2=${sum2},p2=${p2},"
    +"cun4=${cun4},sum4=${sum4},p4=${p4},"
    +"cun6=${cun6},sum6=${sum6},p6=${p6},"
    +"cun8=${cun8},sum8=${sum8},p8=${p8},"
    +"cun10=${cun10},sum10=${sum10},p10=${p10},"
    +"cun12=${cun12},sum12=${sum12},p12=${p12},"
    +"cun14=${cun14},sum14=${sum14},p14=${p14}"
    +"WHERE cycle=${cycle} AND k=${k} AND n=${n}")
    boolean update(CallBack d);

}