package com.neo.sevice.bit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CreKLine {
    // import chain_data.GetHttpData;

    public static final String log_file_name = "ok_tx_log.txt";

    /*
     * interval = [ 3m 5m 15m 30m 1h 2h 4h 6h 8h 12h 1d 3d 1w 1M]
     */
    // limit = [1,1000],d=100
    public static String get_binance_kline(String symbol, String interval, int limit, long start, long end) {
        String url = "https://www.binance.com/api/v1/klines?";
        url += "symbol=" + symbol;
        url += "&interval=" + interval;
        url += "&limit=" + limit;
        url += "&startTime=" + start * 1000;
        url += "&endTime=" + end * 1000;
        url = getStringData(url);
        if (url != "" && url != null)
            return url;
        return "";
    }

    public static String get_binance_kline(String symbol, String interval, int limit, long start) {
        String url = "https://www.binance.com/api/v3/klines?";
        url += "symbol=" + symbol;
        url += "&interval=" + interval;
        url += "&limit=" + limit;
        url += "&startTime=" + start;
        // url +="&endTime="+end*1000;
        url = getStringData(url);
        if (url != "" && url != null)
            return url;
        return "";
    }

    public static String get_binance_kline(String symbol, String interval, int limit) {
        String url = "https://www.binance.com/api/v3/klines?";
        url += "symbol=" + symbol;
        url += "&interval=" + interval;
        url += "&limit=" + limit;
        url = getStringData(url);
        if (url != "" && url != null)
            return url;
        return "";
    }

    public static String get_binance_kline(String symbol, String interval) {
        String url = "https://www.binance.com/api/v3/klines?";
        url += "symbol=" + symbol;
        url += "&interval=" + interval;
        url = getStringData(url);
        if (url != "" || url != null)
            return url;
        return "";
    }

    public static void main(String[] args) {
        insrDB();
    }

    public static void insrDB(){
        long db_time = GetDBStime();
        Date now = new Date();
        while ((now.getTime()-db_time)>1000*60*60*24) {//一天前数据
            System.out.println(now+" "+get_bnbtc_kline(db_time + 5, 1000)+" "+new Date(db_time));
            db_time = GetDBStime();
            now = new Date();
        }
    }

    public static long GetDBStime() {
        long dbBlockLatestHeight = 0l;
        Connection con = dbCon();
        if (con == null) {
            System.out.println("DATABASE CONNECT ERROR !");
            return 0;
        }
        String sql = " SELECT start_time FROM bit.bn_kline_m order by start_time desc limit 1 ";
        try {
            ResultSet rs;
            rs = dbSL(sql);
            dbBlockLatestHeight = rs.getLong(1);
        } catch (Exception e1) {
            // System.out.println("GET DB LATEST_HEIGHT ERROR !");
            // e1.printStackTrace();
            return 0;
        }
        // con.close();
        return dbBlockLatestHeight;
    }

    public static boolean get_bnbtc_kline(long stime, int maxnum) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String data = get_binance_kline("BTCUSDT", "15m", maxnum, stime);
        if (data == "" || data == null) {
            System.out.println("GET WEBDATA ERROR !");
            return false;
        }

        JSONArray b = JSON.parseArray(data);
        //System.out.println(b);
        for (int j = 0;j<b.size(); j++) {
            JSONArray c = b.getJSONArray(j);
            String sql = "insert bit.bn_kline_m(start_time,open_price,top_price,low_price,close_price,";
            sql += "amount,end_time,quantity,exchange_lots,vol_amount,vol_quantity) values(";
            sql += c.getString(0);
            for (int p = 1; p < c.size()-1; p++) {
                sql += "," + c.getDoubleValue(p) ;
            }
            sql += ");";
            //System.out.println(sql);

            if (dbOpr(sql) < 0) {
                System.out.println("DATABASE INSERT ERROR!");
                return false;
            }
        }
        return true;
        // System.out.println(b.getString(3));
    }

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
                while(data!=null)  {
                    da +=data;
                    data=br.readLine();
                }   
                br.close();  
                read.close();  
                input.close();  
                httpUrlConn.disconnect(); 
                return da;
            } catch (MalformedURLException e) {
                return "";
            } catch (IOException e) {
                return "";
            }
        }

        public static Connection con;
        public static ResultSet rs;
        
         public static int dbOpr(String sql){
            Statement sm ;
            int re = 0;
            try{
                sm = con.createStatement();
                re = sm.executeUpdate(sql);
                if (re < 0){
                    con.rollback();
                    sm.close();
                    return re;
                }
            }catch (Exception e){
                System.out.println("DATABASE OPERATION ERROR!"+e);
                return -1;
            }
            try {
                sm.close();
            } catch (SQLException e) {
                System.out.println("DATABASE STATEMENT CLOSE ERROR!");
                return -1;
            }
            return re;
        }
        
        public static ResultSet dbSL(String select){
            //Statement sm ;
    
            try{
                rs = con.createStatement().executeQuery(select);
                 if (!rs.next()){
                 return null;
             }
            }catch (Exception e){
                System.out.println("DATABASE QUERY ERROR!");
                return null;
            }
             return rs;
        }
        
        public static Connection dbCon() { 
                String driver = "com.mysql.jdbc.Driver";
                
                String url = "jdbc:mysql://localhost:3306/bit?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true";
                
                String user = "dabing";
                
                String password = "123456";	
                
                try {
                    Class. forName(driver);
                
                } catch (ClassNotFoundException e ) {
                    con = null;
                    System.out.println("DATABASE CONNECT ERROR CLASS NOT FOUND EXCEPTION!");
                }
                
                try {
                       con = DriverManager. getConnection(url, user, password); 
                } catch (SQLException e ) {
                              con = null;
                              System.out.println("DATABASE CONNECT ERROR SQLEXCEPTION!");
                }
                 return con ;   
        }
    
    
    }