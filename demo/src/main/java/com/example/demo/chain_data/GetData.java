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
import java.util.Date;

import com.alibaba.fastjson.JSON;
public class GetData{
	public static String getHttpData(String addr){     
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
	public static void getData(int block_height)  {
		int page = 1;
		file_out("DATA/"+block_height+".json","[");
		 for(int j=1;j<=page;j++) {
			// Date k = new Date();
			 String url = "https://chain.api.btc.com/v3/block/"+block_height+"/tx?page="+j;
			 url = getHttpData(url);
			 if(url==null) return;
			 if(j!=1)  file_out("DATA/"+block_height+".json",",");
			 file_out("DATA/"+block_height+".json",url);
			 System.out.println(block_height+"-"+j);
			page = (JSON.parseObject(url).getJSONObject("data").getIntValue("total_count"))/51+1;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
		//file_out(log_file_name,new Date().toString());
		 file_out("DATA/"+block_height+".json","]");

	}
	public static void main(String[] args) {
		getData(591001);
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
