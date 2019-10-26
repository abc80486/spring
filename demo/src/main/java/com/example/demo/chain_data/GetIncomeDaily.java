package chain_data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Math;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class GetIncomeDaily{
	
	//1.get increment_rate
	private static List getPriceList(String interval,int number) {
		  List priceList = new ArrayList();
		  String data = getWebData("https://www.binance.com/api/v1/klines?symbol=BTCUSDT&interval="+interval+"&limit="+number);
		  JSONArray dataArrayList = JSON.parseArray(data);
		  for(int i=0;i<dataArrayList.size();i++) {
			  double rate;
			  JSONArray dataArray = dataArrayList.getJSONArray(i);
			  rate = (dataArray.getDouble(4)-dataArray.getDouble(1))/dataArray.getDouble(1);
			  priceList.add(rate);
		  }
		  return priceList;
	}
	public static JSONObject getIncrementRate(int number) {
		JSONObject incrementRate=null;
		List mData = getPriceList("1m",number);
		List hData = getPriceList("1h",number);
		List dData = getPriceList("1d",number);
		List wData = getPriceList("1w",number);
		incrementRate = new JSONObject();
		incrementRate.put("mData", mData);
		incrementRate.put("hData", hData);
		incrementRate.put("dData", dData);
		incrementRate.put("wData", wData);
		//System.out.println(incrementRate);
		return incrementRate;
	}

	private static String getWebData(String addr) {     
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
        	return null;
        } catch (IOException e) {
        	return null;
        }
    }

	private static double getIncomeDaily() throws SQLException {
		String url = "https://chain.api.btc.com/v3/block/latest/";
		url = getWebData(url);
		double difficulty=0.0;
		double mountdaily = 0.0;
		double reward = 0.0;
		double priceMinutly = 0.0;
		double price = 0.0;
		double rate = 0.0;
       	try{
		 difficulty = JSON.parseObject(url).getJSONObject("data").getDouble("difficulty");
		 reward = JSON.parseObject(url).getJSONObject("data").getDouble("reward_block");
       	}catch(Exception e1){
       		System.out.println("JSON ERROR !");
       		return 0;
       	}
        String data = getWebData("https://www.binance.com/api/v1/klines?symbol=BTCUSDT&interval=1m&limit=1");
		JSONArray dataArrayList = JSON.parseArray(data);
		price = dataArrayList.getJSONArray(0).getDoubleValue(4);
       //	String select = "SELECT *,from_unixtime(start_time/1000)  FROM bn_kline_m order by start_time desc limit 1";
       //	Connection con = db_con();
       	// ResultSet rs = db_select(con,select);
        //price = rs.getDouble("close_price");
        //System.out.println(difficulty);
        //System.out.println(price);
        //System.out.println(reward);
        //diff * 2^32 / current_cal = block_time
        //System.out.println(reward/(difficulty*Math.pow(2, 32)/Math.pow(10,12)/60/60/24));
        mountdaily = (reward/Math.pow(10, 8))/(difficulty*Math.pow(2, 32)/Math.pow(10,12)/60/60/24);
        String ratedata = getWebData("https://www.mycurrency.net/US.json");
		rate = JSON.parseObject(ratedata).getJSONArray("rates").getJSONObject(2).getDoubleValue("rate");
        System.out.println(mountdaily*price);
        System.out.println(price*rate);
		return mountdaily*price*rate;
	}

	private static double getRHYCost() {
		double RHYCost = 0.0;
		String webData = getWebData("https://en.rhy.com/rental");
		return RHYCost;
	}
	public static void main(String[] args) throws SQLException {
		System.out.println(getIncomeDaily());
		//double a = 123456789123456789123456789123456789123456789.0;
        //System.out.println(a);
	}
}