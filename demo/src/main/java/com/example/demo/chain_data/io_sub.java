package chain_data;

import java.io.File;
import java.io.FileOutputStream;

public class io_sub {
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