package chain_data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetHttpData{
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
}