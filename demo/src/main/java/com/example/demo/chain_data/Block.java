package chain_data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

//import chain_data.GetHttpData;

public class Block{
	
	public static final String log_file_name = "block_tx_log.txt";
	
	//get latest block height;
	public  static int GetLatestHeight() {
		int latest_height = 0;
		String url = "https://chain.api.btc.com/v3/block/latest";
		
		url = GetHttpData.getStringData(url);
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
		if(DBConn.db_con()==null) {
      		System.out.println("DATABASE CONNECT ERROR !");
      		return 0;
		}
		String sql = " SELECT block_height FROM block_tx order by block_height desc limit 1 ";
		try {
			 ResultSet rs;
			 rs = DBConn.db_sl(sql);
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
	
	public static int block_tx(int block_height,int millsec) {
		Connection con = DBConn.db_con();  
		//ok.con = ok.mysqlconn();
		int page = 1;
       
  	    try {
  	    		con.setAutoCommit(false);
			} catch (SQLException e) {
				System.out.println("SET AUTOCOMMIT ERROR !");
				return 0;
			}
  	  page = (JSON.parseObject(GetHttpData.getStringData("https://chain.api.btc.com/v3/block/"+block_height+"/tx?page=1")).getJSONObject("data").getIntValue("total_count"))/51+1;
	  for(int j=1;j<=page;j++) {	
   	    String key = "";
		key +=" block_height,confirmations,inputs_value,inputs_count,outputs_value,outputs_count,fee,hash,";
  	    key +="is_coinbase,is_double_spend,is_sw_tx,weight,vsize,lock_time,sigops,size,version,witness_hash ";

		String url = "https://chain.api.btc.com/v3/block/"+block_height+"/tx?page="+j;
		url = GetHttpData.getStringData(url);
       	if(url == "" || url == null) {
       		System.out.println("GET WEBDATA ERROR !");
    		return 0;
       	}
		JSONArray tx_list = JSON.parseObject(url).getJSONObject("data").getJSONArray("list");
	  //  page = (JSON.parseObject(url).getJSONObject("data").getIntValue("total_count"))/51+1;
	    
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
	        sql = "insert into block_tx("+ key +") values(" + values + ");";
	        if(DBConn.db_op(sql)<0) {
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
	     	     if(DBConn.db_op(sql_in)<0) {
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
	     	    
	     	     if(DBConn.db_op(sql_ou)<0) {
		            	System.out.println(k+"DATABASE INSERT ERROR!");
		            	return 0;
		        }
	        }
	        try {
				Thread.sleep(millsec *1L);
			} catch (Exception e) {
				System.out.println("THREAD SLEEP ERROR!");
				return 0;
			}      
		}
		
	  }
        try {
			con.commit();
		} catch (SQLException e1) {
			System.out.println(block_height+":DATABASE COMMIT ERROR!");
			return 0;
		}
	    try {
			con.close();
		} catch (SQLException e1) {
			System.out.println(block_height+":DATABASE CLOSE ERROR!");
			return 0;
		}
        System.out.println(block_height+":DATABASE INSERT SUCCESSED.");
		return block_height;
	}
	public boolean BlockTXauto(int num,int millsec) {
		//get latest block height
		Block b=new Block();
		int height = b.GetLatestHeight();
		int dbheight = b.GetDBheight()+1;
		Date time = new Date();
		io_sub.file_out(log_file_name,time.toString());
		io_sub.file_out(log_file_name,"block height:"+dbheight+"  ----->  "+height);
		for(int i=dbheight;i<=height && (i-dbheight)<num;i++) {
			if(block_tx(i,500)==0) {
				System.out.println(i+":DATABASE INSERT ERROR!");				
				return false;
			}
			io_sub.file_out(log_file_name,i+":DATABASE INSERT SUCCESSED." );
		}
		
		return true;
	}
}