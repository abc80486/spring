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
    +"startTime=${startTime},"
    +"cun2=${rate.get(2).getCun()}, "
    +"cun4=${rate.get(4).getCun()}, "
    +"cun6=${rate.get(6).getCun()}, "
    +"cun8=${rate.get(8).getCun()}, "
    +"cun10=${rate.get(10).getCun()}, "
    +"cun12=${rate.get(12).getCun()}, "
    +"cun14=${rate.get(14).getCun()}, "
    +"sum2=${rate.get(2).getSum()}, "
    +"sum4=${rate.get(4).getSum()}, "
    +"sum6=${rate.get(6).getSum()}, "
    +"sum8=${rate.get(8).getSum()}, "
    +"sum10=${rate.get(10).getSum()}, "
    +"sum12=${rate.get(12).getSum()}, "
    +"sum14=${rate.get(14).getSum()}, "
    +"p2=${rate.get(2).getP()}, "
    +"p4=${rate.get(4).getP()}, "
    +"p6=${rate.get(6).getP()}, "
    +"p8=${rate.get(8).getP()}, "
    +"p10=${rate.get(10).getP()}, "
    +"p12=${rate.get(12).getP()}, "
    +"p14=${rate.get(14).getP()} "
    +"WHERE cycle=${cycle} AND k=${k} AND n=${n}")
    boolean update(CallBack d);

}