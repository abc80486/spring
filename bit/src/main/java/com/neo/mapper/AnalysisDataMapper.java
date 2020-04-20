package com.neo.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AnalysisDataMapper {
    @Select("SELECT time,wp1_rt_day,wp1_run_times_day,wp2_rt_day,wp2_run_times_day,wp3_rt_day,wp3_run_times_day,crew1_rt_day,crew1_run_times_day,crew2_rt_day,crew2_run_times_day,aircon_rt_day,aircon_run_times_day FROM ANALYSIS_DATA WHERE time>=#{sdate} && time<=#{edate}")
    List<AnalysisData> getRuntingData(@Param("sdate") String sdate,@Param("edate") String edate);
}