package chain_data;

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
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class block_tx{
	public static Connection con;
	public static ResultSet rs=null;
	
 	public static int db_op(String sql){
		Statement sm ;
        int re = 0;
        try{
        	sm = con.createStatement();
            re = sm.executeUpdate(sql);//·µ»ØÐÐÊý£»
            if (re < 0){
                con.rollback();
                sm.close();
                return re;
            }
        }catch (Exception e){
        	e.printStackTrace();
        	System.out.println("DATABASE OPERATION ERROR!");
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
	
	public static ResultSet db_sl(String select){
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
	
	public block_tx() {
		//db_con();
	}
	public static void db_con() { 
		    //Connection con;
		    //con = null;
	        String driver = "com.mysql.jdbc.Driver";
	        
	        String url = "jdbc:mysql://47.240.27.182:3306/ok";
	        
	        String user = "root";
	        
	        String password = "123456";	
	        
	        try {
                Class. forName(driver);
            
            } catch (ClassNotFoundException e ) {
            	con = null;
                System.out.println("DATABASE CONNECT ERROR CLASS NOT FOUND EXCEPTION!");
            }
	        
	        try {
	               con = DriverManager.getConnection(url, user, password); 
	               
	        } catch (SQLException e ) {
	                      con = null;
	                      System.out.println("DATABASE CONNECT ERROR SQLEXCEPTION!");
	        } 
	}
	
	//
	public static String getStringData(String addr) {     
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
	
	//
	public  static int GetLatestHeight() {
		int latest_height = 0;
		String url = "https://chain.api.btc.com/v3/block/latest";
		
		url = getStringData(url);
       	if(url == "" || url == null) {
       		System.out.println("GET WEBDATA ERROR !");
    		return 0;
       	}
       	try {
       	    latest_height = JSON.parseObject(url).getJSONObject("data").getIntValue("height");
       	    return latest_height;
       	}
       	catch(Exception e) {
       		System.out.println("JSON ERROR !");
       	    e.printStackTrace();
       	    return latest_height;
       	}
		
	}
	//get db block height
	public static int GetDBheight() {
		
        // DBConn.db_con();
		if(con==null) {
      		System.out.println("DATABASE CONNECT ERROR !");
      		return 0;
		}
		String sql = " SELECT block_height FROM block_tx order by block_height desc limit 1 ";
		try {
			 ResultSet rs;
			 rs = db_sl(sql);
			 if(rs==null)  return 0;
			 else return rs.getInt(1);
			
		} catch (Exception e1) {
     		System.out.println("GET DB LATEST_HEIGHT ERROR !");
     		//e1.printStackTrace();
      		return -1;
		}
		//con.close();
		//return  dbBlockLatestHeight;
	}
	
	public static Runtime rt = Runtime.getRuntime();
	public static int get_tx_data(int block_height,int millsec) {
		//db_con();  
		int page = 1;
       //System.out.println(rt.freeMemory()/1000.0/1000);
  	    try {
  	    		con.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("SET AUTOCOMMIT ERROR !");
				return 0;
			}
  	  
  	 
  	  for(int j=1;j<=page;j++) {
		
   	    String key = "";
		key +=" block_height,confirmations,inputs_value,inputs_count,outputs_value,outputs_count,fee,hash,";
  	    key +="is_coinbase,is_double_spend,is_sw_tx,weight,vsize,lock_time,sigops,size,version,witness_hash ";

		String url = "https://chain.api.btc.com/v3/block/"+block_height+"/tx?page="+j;
		url = getStringData(url);
		Date st = new Date();
       	if(url == "" || url == null) {
       		System.out.println("GET WEBDATA ERROR !");
    			return 0;
       	}
       	JSONArray tx_list=null;
       	try{
		 tx_list = JSON.parseObject(url).getJSONObject("data").getJSONArray("list");
       	}catch(Exception e1){
       		System.out.println("JSON ERROR !");
       		return 0;
       	}
	    page = (JSON.parseObject(url).getJSONObject("data").getIntValue("total_count"))/51+1;
	    
	    //System.out.println(page);
		for(int i=0;i<tx_list.size();i++) {
			JSONObject tx = tx_list.getJSONObject(i);
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
	        sql = "insert into block_tx("+ key +") values(" + values + ");";
	        if(db_op(sql)<0) {
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
	     	     if(db_op(sql_in)<0) {
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
	     	    
	     	     if(db_op(sql_ou)<0) {
		            	System.out.println(k+":DATABASE INSERT ERROR!");
		            	return 0;
		        }
	        }
	        in.clear();ou.clear();tx.clear();sql=null;  
		}
		Date end = new Date();
		long ts = end.getTime()-st.getTime();
			 try {
			 	if(millsec*1l>ts) Thread.sleep(millsec*1l-ts);
				} catch (Exception e) {
					System.out.println("THREAD SLEEP ERROR!");
					return 0;
				}
		
	  }
        try {
			con.commit();
		} catch (SQLException e1) {
			System.out.println(block_height+":DATABASE COMMIT ERROR!");
			return 0;
		}
	    
        System.out.println(block_height+":DATABASE INSERT SUCCESSED.");
		return block_height;
	}
	public static void main(String[] args) {
		final String log_file_name = "block_tx_log.txt";
		//System.out.println(rt.freeMemory()/1000.0/1000);
		db_con();
		int s = GetDBheight()+1;
		int e = GetLatestHeight();
		//file_out(log_file_name,new Date().toString());
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date k = new Date();
		for(;s<e;s++) {
			if(get_tx_data(s, 500)<=0) {
				file_out(log_file_name,new Date().toString());
				file_out(log_file_name,s+":DATABASE INSERT ERROR!");
				file_out(log_file_name,(new Date().getTime()-k.getTime())+"");
				System.out.println(s+":DATABASE INSERT ERROR!");
				return;
			}
			Date k2 = new Date();
			System.out.println((k2.getTime()-k.getTime())/1000.0);
			if(k2.getTime()-k.getTime()>2*60*1000l) return;
		}
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
