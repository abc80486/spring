package com.neo.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class Use {
    public static String getStringData(String addr) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
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
            while (data != null) {
                da += data;
                data = br.readLine();
            }
            br.close();
            read.close();
            input.close();
            httpUrlConn.disconnect();
            return da;
        } catch (Exception e) {
            return null;
        }
    }

    public static double cal(JSONArray d,int times,double f,int k,int n){
        int size = d.size();

        double p1,p2,p3=0;
        long t1,t2,t3=0;
        int cun=0,sum=0;

        JSONArray t = d.getJSONArray(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        //最低价
        p1 = t.getDoubleValue(3);
        p2 = t.getDoubleValue(2); //最高价
        t1 = t.getLongValue(0);//时间
        t2 = t1;
        //0 时间 1 开盘价 2 最高 3 最低 4 收盘 5 量
        double rate=0;

        for(int i=1;i<size-times;i++){

            int k1=0,k2=0;
            double rate1,rate2;

            for(int j=i;j<i+times;j++){
                t = d.getJSONArray(j);
                long tem = t.getLongValue(0);
                if(t.getDoubleValue(3)<p1) {
                    p1=t.getDoubleValue(3);
                    k1 = j;
                    t1 = tem;
                }
                if(t.getDoubleValue(2)>p2) {
                    p2=t.getDoubleValue(2);
                    k2 = j;
                    t2 = tem;
                }
                //初步判断为下跌行情
                int indx = 0;

                //当最高价涨幅大于4时，
                if((p2-p1)/Math.max(p2, p1)*100>=f){
                    sum++;
                    //1.当出现单根柱子时
                    if(k1==k2){
                        JSONArray te = d.getJSONArray(k1);
                        //开盘价小于收盘价,上涨
                        if(te.getDoubleValue(1)<=te.getDoubleValue(4)){
                            rate1 = (p2 - p1)/p1*100;indx=1;
                        }else{
                            rate1 = (p2 -p1)/p2*100;
                        }
                    }else{   //         2.判断上涨或者下跌
                        if(t1<=t2){
                            rate1 = (p2 - p1)/p1*100;indx=1;//上涨
                        }else{
                            rate1 = (p2 - p1)/p2*100;//下跌
                        }
                    }
                    System.out.println(p1+" "+p2+" "+sdf.format(new Date(t1)));
                    int ind = k1>k2?k1:k2;
                    for(int kp=ind;kp<ind+k*times;kp++){
                        double tum = d.getJSONArray(kp).getDoubleValue(3);
                        if(tum<p3){
                            p3 = tum;
                            t3 = d.getJSONArray(kp).getLongValue(0);
                        }
                        //如果上涨
                        if(indx==1){
                            if(tum<p3){
                                p3 = tum;
                                t3 = d.getJSONArray(kp).getLongValue(0);
                            }                                
                            if((p2 - p3)/p2*100>f/n){
                                cun++;
                                //System.out.println(new Date(t1)+" "+p1);
                                //System.out.println(new Date(t2)+" "+p2);
                                //System.out.println(new Date(t3)+" "+p3);
                                //System.out.println("");
                                //i = ind - 1;
                                break;
                                
                            }
                        }else{
                            if(d.getJSONArray(kp).getDoubleValue(2)>p3){
                                p3 = d.getJSONArray(kp).getDoubleValue(2);
                                t3 = d.getJSONArray(kp).getLongValue(0);
                            }    
                            if((p3-p1)/p1*100 > f/n) {cun++;
                                //System.out.println(new Date(t1)+" "+p1);
                                //System.out.println(new Date(t2)+" "+p2);
                                //System.out.println(new Date(t3)+" "+p3);
                                //System.out.println("");
                                //i = ind - 1;
                                break;}
                        }
                    }
                    break;
                }
            }
        }
        System.out.println(sum+"  "+cun);
        return cun;
    }
    public static double calRaise(Date s,Date e,String interval){
        String data =  get_binance_kline("BTCUSDT",interval,s.getTime(),e.getTime());
        JSONArray da= JSON.parseArray(data);
        int size = da.size(); 

        double low = da.getJSONArray(0).getDoubleValue(3);
        double high = da.getJSONArray(0).getDoubleValue(2);
        Date t1 = da.getJSONArray(0).getDate(0);
        Date t2 = t1;
        int k=0;
        JSONArray tem;
        for(int i=1;i<size;i++){
            tem = da.getJSONArray(i);
            if(tem.getDoubleValue(3)<low) {
                low = tem.getDoubleValue(3);
                t1 = tem.getDate(0);
                k = i;
            }
            if(tem.getDoubleValue(2)>high) {
                high = tem.getDoubleValue(2);
                t2 = tem.getDate(0);
            }
        }
        System.out.println(""+t1+" "+low+" "+high+" "+t2+" "+(high-low));
        if(t1==t2) {
            //判断是上涨还是下跌
            if(da.getJSONArray(k).getDoubleValue(4)>=da.getJSONArray(k).getDoubleValue(4)){
                return (high-low)/low*100;
            }else return (high-low)/high*-100;
        }else if(t2.getTime()>t1.getTime()) return (high-low)/low*100;
        else return (high-low)/high*-100;
    }

    public static void main(String arg[]) throws ParseException {
        //System.out.println(data);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        double re = calRaise(sdf.parse("2020-03-03 00:00:00"), sdf.parse("2020-03-15 00:00:00"), "1h");
        System.out.println(re);
        //cal(da, 5, 4.0, 2, 4);
/*
        for(int i=0;i<size;++i){

            JSONArray t = da.getJSONArray(i);
            Date time = t.getDate(0);
            double open = t.getDoubleValue(1);
            double high = t.getDoubleValue(2);
            double low = t.getDoubleValue(3);
            double close = t.getDoubleValue(4);

            boolean k = close >= open;
            double tem = k?low:high;
            //振幅
            double r = (high-low)/tem*100.0;
            //回调幅度
            double p = k?(high-close)/high*100:(close-low)/low*100;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

            if(r>=3.0){
                //System.out.print(sdf.format(time)+"     ");
                //System.out.println(r+"   "+p);
            }
        }
        //System.out.println(da.getJSONArray(0).getDoubleValue(1));
*/
    }
    public static String get_binance_kline(String symbol,String interval,int limit,long start,long end ) {
		String url = "https://www.binance.com/api/v3/klines?";
		url += "symbol="+symbol ;
		url +="&interval="+interval;
		url +="&limit="+limit;
		url +="&startTime="+start;
		url +="&endTime="+end;
		url = getStringData(url);
		if(url != "" && url != null) return url;
		return "";
    }
    
    public static String get_binance_kline(String symbol,String interval,long start,long end ) {
		String url = "https://www.binance.com/api/v3/klines?";
		url += "symbol="+symbol ;
		url +="&interval="+interval;
		url +="&startTime="+start;
		url +="&endTime="+end;
		url = getStringData(url);
		if(url != "" && url != null) return url;
		return "";
	}

	public static String get_binance_kline(String symbol,String interval,int limit,long start) {
		String url = "https://www.binance.com/api/v3/klines?";
		url += "symbol="+symbol ;
		url +="&interval="+interval;
		url +="&limit="+limit;
		url +="&startTime="+start;
		//url +="&endTime="+end*1000;
		url = getStringData(url);
		if(url != "" && url != null) return url;
		return "";
	}
	public static String get_binance_kline(String symbol,String interval,int limit) {
		String url = "https://www.binance.com/api/v3/klines?";
		url += "symbol="+symbol;
		url +="&interval="+interval;
		url +="&limit="+limit;
		url = getStringData(url);
		if(url != "" && url != null) return url;
		return "";
	}
	public static String get_binance_kline(String symbol,String interval) {
		String url = "https://www.binance.com/api/v3/klines?";
		url += "symbol="+symbol ;
		url +="&interval="+interval;
		url = getStringData(url);
		if(url != "" || url != null) return url;
		return "";
	}

}