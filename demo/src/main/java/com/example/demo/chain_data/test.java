package chain_data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
public class test{
	
	public static void main(String[] args) throws Exception  {
		ArrayList a = new ArrayList();
		
		
		JSONObject js = new JSONObject();
		js.put("a",1);
		js.put("b",2);
		js.entrySet();
		JSONObject js1 = js;
		System.out.print(js.hashCode());
	}
	 private static List getPriceList() {
		  List priceList = new ArrayList();
		  String data = getWebData("https://www.binance.com/api/v1/klines?symbol=BTCUSDT&interval=1m&limit=11");
		  JSONArray dataArrayList = JSON.parseArray(data);
		  for(int i=1;i<=10;i++) {
			  double price;
			  JSONArray dataArray = dataArrayList.getJSONArray(i);
			  price = dataArray.getDouble(4);
			  priceList.add(price);
			  //System.out.println(price);
		  }
		  Map webData = new HashMap();
		  JSONObject a = new JSONObject();
		  a.put("data", priceList);
		  webData.put("data",priceList);
		  System.out.println(a);
		  return priceList;
	  }
	public static void test_9_6() throws Exception {
		String regex = "price";    
	    Pattern pattern = Pattern.compile(regex);    

	    String candidate = getWebData("https://www.okex.com/otc");  
	    //System.out.println(candidate);
	    Matcher matcher = pattern.matcher(candidate);    
	    if (matcher.find()) {      
	     System.out.println("GROUP 0:" + matcher.group(0));    
	    }  
	}
	public static void test_m() {
		Runtime rt = Runtime.getRuntime();
		System.out.println(rt.freeMemory()/1000.0/1000);
		int a;
		block_tx b = new block_tx();
		
		for(int i=41;i<50;i++) {
			//int a;
			//a=i;
			
			b.db_con();
			if(block_tx.get_tx_data(i, 600)<=0) break;
			
			System.out.println(rt.freeMemory()/1000.0/1000);
			
		}
		//
	}
	public static String captureJavascript() throws Exception {  
	    String strURL = "https://www.okex.com/otc";  
	    URL url = new URL(strURL);  
	    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();  
	    InputStreamReader input = new InputStreamReader(httpConn  
	            .getInputStream(), "utf-8");  
	    BufferedReader bufReader = new BufferedReader(input);  
	    String line = "";  
	    StringBuilder contentBuf = new StringBuilder();  
	    while ((line = bufReader.readLine()) != null) {  
	        contentBuf.append(line);  
	    }  
	    return  contentBuf.toString();  
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
}