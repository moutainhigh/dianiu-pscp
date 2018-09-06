/**
 * 
 */
package test.edianniu.mis.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author elliot.chen
 *
 */
public class FileUtil {
	/**
	 * 
	 * @param content
	 * @param input
	 */
	public static  void writeFile(final byte[] content,final String input){
		File file=new File(input);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		OutputStream output=null;
		try {
			output=new FileOutputStream(file,false);
			output.write(content);
			output.flush();
			output.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(output!=null){
				try {
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				output=null;
			}
			
		}
		
	}
}
