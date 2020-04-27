package com.neo.services.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.neo.model.MinuteData;

public class GetKlineData {
    public static List<MinuteData> Kline_15m(long startTime,int limit){
        return get_binance_kline("BTCUSDT", "15m", limit, startTime);
    }
    public static List<MinuteData> Kline_15m(long startTime){
        return get_binance_kline("BTCUSDT", "15m",Integer.MAX_VALUE, startTime);
    }

    public static String getHttpData(String addr) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
            String da = "";
            try {  
                URL url = new URL(addr);  
                HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
                httpUrlConn.setDoInput(true);  
                httpUrlConn.setRequestMethod("GET");  
                httpUrlConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
                
                InputStream input = httpUrlConn.getInputStream();         
                InputStreamReader read = new InputStreamReader(input, "utf-8");
                BufferedReader br = new BufferedReader(read);  
                
                String data = br.readLine();
                while(data!=null)  {
                    da +=data;
                    data=br.readLine();
                }   
                br.close();  
                read.close();  
                input.close();  
                httpUrlConn.disconnect(); 
                return da;
            } catch (Exception e) {
                return "";
            }
        }

    //[[1587816000000,"7532.35000000","7564.49000000","7530.66000000","7551.56000000","1087.18523100",1587819599999,"8206001.51517127",19163,"533.22856300","4025137.81507498","0"],
    //[1587819600000,"7551.76000000","7594.97000000","7551.25000000","7594.96000000","1839.32259800",1587823199999,"13926088.18177487",23991,"1038.16574200","7861090.92172468","0"],
    //[1587823200000,"7594.96000000","7598.42000000","7560.82000000","7588.42000000","1330.83181200",1587826799999,"10086974.52437336",16655,"688.85145200","5222118.89406846","0"]]

    public static List<MinuteData> get_binance_kline(String symbol, String interval, int limit, long start) {
        String url = "https://www.binance.com/api/v3/klines?";
        url += "symbol=" + symbol;
        url += "&interval=" + interval;
        url += "&limit=" + limit;
        url += "&startTime=" + start;
        // url +="&endTime="+end*1000;
        url = getHttpData(url);
//System.out.print(url);
        List<MinuteData> re = new ArrayList<MinuteData>();
        JSONArray sd = JSON.parseArray(url);
        for(int i=0;i<sd.size();i++){
            JSONArray tm = sd.getJSONArray(i);
            MinuteData temp = new MinuteData();
            temp.setStart_time(tm.getLongValue(0));
            temp.setOpen_price(tm.getDoubleValue(1));
            temp.setTop_price(tm.getDoubleValue(2));
            temp.setLow_price(tm.getDoubleValue(3));
            temp.setClose_price(tm.getDoubleValue(4));
            temp.setAmount(tm.getDoubleValue(5));
            temp.setEnd_time(tm.getLongValue(6));
            temp.setQuantity(tm.getDoubleValue(7));
            temp.setExchange_lots(tm.getDoubleValue(8));
            temp.setVol_amount(tm.getDoubleValue(9));
            temp.setVol_quantity(tm.getDoubleValue(10));
            re.add(temp);
        }
        return re;
    }

}