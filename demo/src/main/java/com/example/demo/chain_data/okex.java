package chain_data;
import com.alibaba.fastjson.JSONObject;

//import db_conn.TestJDBC;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Map;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class okex{
	
	private Connection con ;
	private Statement sm ;
	private ResultSet rs ;
	
	public static final String log_file_name = "get_block_log.txt";
	
 	public static ArrayList<String> test(String date_Start,String date_End,String format) {
		ArrayList<String> strArray = new ArrayList<String> (); 
		SimpleDateFormat ft = new SimpleDateFormat(format);
		Date start = new Date();
		Date end ;
		try {
			start = ft.parse(date_Start);
		} catch (ParseException e) {
			return strArray;
		}
		try {
			end = ft.parse(date_End);
		} catch (ParseException e) {
			return strArray;
		}
	    //System.out.println(ft.format(start)+" "+ft.format(end)+"\n");
	    Calendar nextTime = Calendar.getInstance();
	    while(start.before(end)) {
	    	strArray.add(ft.format(start));
	    	nextTime.setTime(start);
		    nextTime.add(Calendar.DATE, 1);
		    start = nextTime.getTime();
	    }
	    strArray.add(ft.format(end));
	   return strArray;
	}

	public static int tx_test(okex ok,int block_height,String table_name,int millsec) {
   
		ok.con = ok.mysqlconn();
		int page = 1;
  	    try {
				ok.con.setAutoCommit(false);
			} catch (SQLException e) {
				System.out.println("SET AUTOCOMMIT ERROR !");
				return 0;
			}
        
	  for(int j=1;j<=page;j++) {
		  		
   	    String key = "";
		key +=" block_height,confirmations,inputs_value,inputs_count,outputs_value,outputs_count,fee,hash,";
  	    key +="is_coinbase,is_double_spend,is_sw_tx,weight,vsize,lock_time,sigops,size,version,witness_hash ";

		String url = "https://chain.api.btc.com/v3/block/"+block_height+"/tx?page="+j;
		url = getWebData(url);
       	if(url == "" || url == null) {
       		System.out.println("GET WEBDATA ERROR !");
    		return 0;
       	}
		JSONArray tx_list = JSON.parseObject(url).getJSONObject("data").getJSONArray("list");
	    page = (JSON.parseObject(url).getJSONObject("data").getIntValue("total_count"))/51+1;
	    
	    //System.out.println(page);
		for(int i=0;i<tx_list.size();) {
			JSONObject tx = tx_list.getJSONObject(i++);
      	    String values = "";
     	    String sql = "";
			//System.out.println(tx.getLongValue("inputs_value"));
			//System.out.println(tx.getLongValue("outputs_value"));
			values += tx.getIntValue("block_height")+",";
			values += tx.getIntValue("confirmations")+",";
			values += tx.getLongValue("inputs_value")+",";
			values += tx.getIntValue("inputs_count")+",";
			
			values += tx.getLongValue("outputs_value")+",";
			values += tx.getIntValue("outputs_count")+",";
			values += tx.getLongValue("fee")+",";
			values += "'"+tx.getString("hash")+"'"+",";
			values += tx.getBoolean("is_coinbase")+",";
			values += tx.getBoolean("is_double_spend")+",";
			values += tx.getBoolean("is_sw_tx")+",";
			
			values += tx.getIntValue("weight")+",";
			values += tx.getIntValue("vsize")+",";
			values += tx.getLongValue("lock_time")+",";
			values += tx.getIntValue("sigops")+",";
			values += tx.getIntValue("size")+",";
			values += tx.getIntValue("version")+",";
			values += "'"+tx.getString("witness_hash") +"' ";
	        sql = "insert into "+table_name+"("+ key +") values(" + values + ");";
	        if(ok.db_op(sql)<0) {
	            	System.out.println(i+"DATABASE INSERT ERROR!");
	            	return 0;
	        }
	        
	        JSONArray in = tx.getJSONArray("inputs");
	        for(int k=0;k<in.size();) {
				JSONObject temp_in = in.getJSONObject(k++);
				String key_in = "";
	      	    String values_in = "";
	     	    String sql_in = "";
	     	    key_in +=" block_height,hash,prev_addresses,prev_position,prev_tx_hash,prev_type,prev_value,sequence ";
	     	    values_in +=tx.getIntValue("block_height")+",";
	     	    values_in += "'"+tx.getString("hash")+"'"+",";
	     	    values_in += "'"+temp_in.getString("prev_addresses")+"'"+",";
	     	    values_in +=temp_in.getIntValue("prev_position")+",";
	     	    values_in += "'"+temp_in.getString("prev_tx_hash")+ "'"+",";
	     	    values_in += "'"+temp_in.getString("prev_type")+ "'"+",";
	     	    values_in +=temp_in.getLongValue("prev_value")+",";
	     	    values_in +=temp_in.getLongValue("sequence")+" ";
	     	    sql_in = "insert into block_input("+ key_in +") values(" + values_in + ");";
	     	     if(ok.db_op(sql_in)<0) {
		            	System.out.println(k+"DATABASE INSERT ERROR!");
		            	return 0;
		        }
	        }
	        JSONArray ou = tx.getJSONArray("outputs");
	        for(int k=0;k<ou.size();) {
				JSONObject temp_ou = ou.getJSONObject(k++);
				String key_ou = "";
	      	    String values_ou = "";
	     	    String sql_ou = "";
	     	    key_ou +=" block_height,hash,addresses,value,type,spent_by_tx,spent_by_tx_position ";
	     	    values_ou += tx.getIntValue("block_height")+",";
	     	    values_ou += "'"+tx.getString("hash")+"'"+",";
	     	    values_ou += "'"+temp_ou.getString("addresses")+"'"+",";
	     	    values_ou +=temp_ou.getLongValue("value")+",";
	     	    values_ou += "'"+temp_ou.getString("type")+ "'"+",";
	     	    values_ou += "'"+temp_ou.getString("spent_by_tx")+ "'"+",";
	     	    values_ou +=temp_ou.getIntValue("spent_by_tx_position")+" ";
	     	    sql_ou = "insert into block_output("+ key_ou +") values(" + values_ou + ");";
	     	    
	     	     if(ok.db_op(sql_ou)<0) {
		            	System.out.println(k+"DATABASE INSERT ERROR!");
		            	return 0;
		        }
	        }
	           
		}
		call(millsec);
	  }
        try {
			ok.con.commit();
		} catch (SQLException e1) {
			System.out.println(block_height+":DATABASE COMMIT ERROR!");
			return 0;
		}
	    try {
			ok.con.close();
		} catch (SQLException e1) {
			return 0;
		}
        System.out.println(block_height+":DATABASE INSERT SUCCESSED.");
		return 1;
	}
	public static void main(String[] args) throws Exception {
		//Date start = new Date();
		//file_out(log_file_name,start.toString(),true);
		getBlockAuto(30,600) ;
	}
	public static  void call(int n) {
        try {
			Thread.sleep(n *1L);
		} catch (Exception e) {
			return;
		}
        
    }
	public static int GetLatestHeight() {
		String url = "https://chain.api.btc.com/v3/block/latest";
		
		url = getWebData(url);
       	if(url == "" || url == null) {
       		System.out.println("GET WEBDATA ERROR !");
    		return 0;
       	}
       	
		int latest_height = JSON.parseObject(url).getJSONObject("data").getIntValue("height");
		if(latest_height<1 ) {
      		System.out.println("GET LATEST_HEIGHT ERROR !");
      		return 0;
		}
		else return latest_height;
	}
	public static int getBlockAuto(int max_num,int sleepTime) {
		/*
		
		//获得最新快数据的高度
		//获得数据库中最后插入数据的高度
		//当数据库块高度《最新快高度 ，获得数据库块高度加1到最新块（包含）之间的的块数据
		//将数据插入插入数据库，并计数，当数量大于最大数量时停止；
		 * 
		*/
		//（1）获得最新块高度
		Date start = new Date();
		file_out(log_file_name,start.toString(),true);
		String url = "https://chain.api.btc.com/v3/block/latest";
		
		url = getWebData(url);
       	if(url == "" || url == null) {
       		System.out.println("GET WEBDATA ERROR !");
       		//file_out(log_file_name,);
    		return 0;
       	}
       	
		int latest_height = JSON.parseObject(url).getJSONObject("data").getIntValue("height");
		if(latest_height<1 ) {
      		System.out.println("GET LATEST_HEIGHT ERROR !");
      		return 0;
		}
		//将块高度写出文件
		file_out(log_file_name,"block height :"+latest_height+"");
		//(2)获取数据库最新块高度
		int dbBlockLatestHeight=0;
        okex ok = new okex();
		ok.con = ok.mysqlconn();
		if(ok.con==null) {
      		System.out.println("DATABASE CONNECT ERROR !");
      		return 0;
		}
		String sql = " SELECT height FROM block order by height desc limit 1 ";
		try {
			 ResultSet rs = null;
			 rs = ok.db_sle(sql);
			 if (!rs.next()){
                 return 0;
             }
			 //dbBlockLatestHeight = Integer.valueOf(rs.getString("height"));
			 dbBlockLatestHeight = rs.getInt("height");
		} catch (SQLException e1) {
     		System.out.println("GET DB LATEST_HEIGHT ERROR !");
      		return 0;
		}
		if(dbBlockLatestHeight<1) {
      		System.out.println("GET DB LATEST_HEIGHT ERROR !");
      		return 0;
		}
		//DATA INSERT DATABASE
		int k=0;
		for(int i=dbBlockLatestHeight+1;i<=latest_height && k<max_num;i++) {
			if(getBlock(i)) {
				k++;
				System.out.println(i+" : BLOCK DATA INSERT SUCCESSED!");
				file_out(log_file_name,i+" : BLOCK DATA INSERT SUCCESSED!");
			}
			else {
				return k;
			}
			call(sleepTime);
		}
		Date end = new Date();
		long cd = end.getTime()-start.getTime();
		file_out(log_file_name,"spent time(s) :"+cd/1000.0+"\n");
		return k;
	}
 	public static void getBlockDate() {
          okex ok = new okex();
          
		ok.con = ok.mysqlconn();
		ArrayList<String> dateList;
		dateList=test("20140102","20150101","yyyyMMdd");
	 String url="";
	 String str="";
	 //int k=0;
      for(int j=0;j<dateList.size();j++) {
		 url = "https://chain.api.btc.com/v3/block/date/"+dateList.get(j);
		 str = "";
       	 str = getWebData(url);
    	
    	     if(str == "" || str == null) {
			System.out.println("Get Data Error!");
			System.exit(0);
		}
		JSONObject datas = JSON.parseObject(str);
        JSONArray blocks = datas.getJSONArray("data");
        JSONObject block_data = null;
        int k= blocks.size();
        //System.out.println(dateList.get(j));
        try {
				ok.con.setAutoCommit(false);
			} catch (SQLException e) {
				return;
			}
        for(int i=k-1;i>=0;i--) {
            block_data = blocks.getJSONObject(i);
      	    String sql = "";
      	    String key = "";
      	    String values = "";
      	    boolean flag = true;
            for (Map.Entry<String, Object> entry : block_data.entrySet()) {
            	if(flag == true) {
            		key += entry.getKey();
            		values += "\'"+entry.getValue()+"\'";
            	}
            	else {
            		key +="," + entry.getKey();
            		values += ",\'" + entry.getValue() +"\'";
            	}
            	flag = false;
            }
            sql = "insert into test("+ key +") values(" + values + ");";
            if(ok.db_op(sql)<0) {
            	System.out.println(block_data.getIntValue("height")+"DATABASE INSERT ERROR!");
            	//System.exit(0);
            	return;
            }       
        }
       
        try {
			ok.con.commit();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			return;
		}
         System.out.println(dateList.get(j)+":"+block_data.getIntValue("height"));
         call(600);
       }
		
	}
	public static boolean getBlock(int height) {
        okex ok = new okex();
		ok.con = ok.mysqlconn();
		String url = "https://chain.api.btc.com/v3/block/"+String.valueOf(height);
    	url = getWebData(url);
    	if(url == "" || url == null) {
			System.out.println(height+":GET BLOCK DATA ERROR!");
			return false;
		}
		JSONObject block_data = JSON.parseObject(url).getJSONObject("data");
        
  	    String sql = "";
  	    String key = "";
  	    String values = "";
  	    boolean flag = true;
        for (Map.Entry<String, Object> entry : block_data.entrySet()) {
        	if(flag == true) {
        		key += entry.getKey();
        		values += "\'"+entry.getValue()+"\'";
        	}
        	else {
        		key +="," + entry.getKey();
        		values += ",\'" + entry.getValue() +"\'";
        	}
        
        	flag = false;
        }
        sql = "insert into block("+ key +") values(" + values + ");";
        
        if(ok.db_op(sql)<0) {
        	System.out.println(height+"BLOCK DATA INSERT ERROR!");
        	return false;
        }
        return true;
	}
	
	public static   String getWebData(String addr) {     
		String da = "";
        try {  
            //建立连接  
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
           // e.printStackTrace();
        	return "";
        } catch (IOException e) {
            //e.printStackTrace();
        	return "";
        }
    }
	//增、删、改
	public int db_op(String sql){
        int re = 0;
        try{
        	//con.setAutoCommit(false);
        	sm = con.createStatement();
            re = sm.executeUpdate(sql);//返回行数；
            if (re < 0){
                con.rollback();
                sm.close();
                return re;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
	//查
	public ResultSet db_sle(String select){
        try{
            sm = con.createStatement();
            rs = sm.executeQuery(select);
            return rs;
        }catch (Exception e){
            return null;
        }
    }
	
	public  Connection mysqlconn() { 
	        String driver = "com.mysql.jdbc.Driver";
	        
	        String url = "jdbc:mysql://47.240.27.182:3306/ok";
	        
	        String user = "root";
	        
	        String password = "123456";	
	        
	        try {
                Class. forName(driver);
            
            } catch (ClassNotFoundException e ) {
            	con = null;
              }
	        
	        try {
	               con = DriverManager. getConnection(url, user, password); 
	        } catch (SQLException e ) {
	                      con = null;
	        }
	         return con ;
	       
	}
	public static boolean file_out(String file_name,String data,boolean out_type) {
	    try (FileOutputStream fos = new FileOutputStream(file_name,out_type)) {
	        fos.write(data.getBytes()); 
	       // String lineSeparator = System.getProperty("line.separator");
		    String lineSeparator = System.getProperty("line.separator");
		    fos.write(lineSeparator.getBytes());
	        fos.flush();
	        new File(file_name);
	        return true;
	      } catch (Exception e2) {
	        return false;
	      }
	}
	public static boolean file_out(String file_name,String data) {
	    try (FileOutputStream fos = new FileOutputStream(file_name,true)) {
	        fos.write(data.getBytes()); 
	       // String lineSeparator = System.getProperty("line.separator");
		    String lineSeparator = System.getProperty("line.separator");
		    fos.write(lineSeparator.getBytes());
	        fos.flush();
	        new File(file_name);
	        return true;
	      } catch (Exception e2) {
	        return false;
	      }
	}
}

