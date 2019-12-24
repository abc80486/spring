package com.neo.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class AnalysisData implements Serializable {
   private int id;
   private int wp1_rt_day;
   private int wp1_run_times_day;
   private int wp2_rt_day;
   private int wp2_run_times_day;
   private int wp3_rt_day;
   private int wp3_run_times_day;
   private int crew1_rt_day;
   private int crew1_run_times_day;
   private int aircon_rt_day;
   private int aircon_run_times_day;

   private Date time;

   public AnalysisData(){
        super();
   }

}