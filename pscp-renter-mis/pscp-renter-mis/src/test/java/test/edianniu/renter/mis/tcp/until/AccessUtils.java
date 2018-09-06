package test.edianniu.renter.mis.tcp.until;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class AccessUtils {
	private static final Logger logger = LoggerFactory.getLogger(AccessUtils.class);
	//private static final Deflater COMPRESSOR = new Deflater(Deflater.BEST_COMPRESSION);
	//private static final Inflater DECOMPRESSER = new Inflater();
	
	public static byte[] zip(byte[] input) {
		if(null == input){
			return new byte[0];
		}
		
		// Create the compressor with highest level of compression
		Deflater deflater = new Deflater(Deflater.DEFAULT_COMPRESSION);
		deflater.setInput(input);
		deflater.finish();
		
	    ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
	    // Compress the data
	    byte[] buf = new byte[1024];
	    while (!deflater.finished()) {
	        int count = deflater.deflate(buf);
	        bos.write(buf, 0, count);
	    }
	    // Get the compressed data
	    byte[] ret = bos.toByteArray();
	    try {
			bos.close();
		} catch (IOException e) {}
		
	    bos = null;
	    deflater = null;
	    
	    return ret;
	}
	
	public static byte[] unzip(byte[] input) {
		if(null == input){
			return new byte[0];
		}
		Inflater inflater = new Inflater();
	    inflater.setInput(input, 0, input.length);
	     
	    ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
		    
	    // deCompress the data
	    byte[] buf = new byte[1024];
	    try {
		    while (!inflater.finished()) {
					int count = inflater.inflate(buf);
					bos.write(buf, 0, count);
		    }
		    byte[] ret = bos.toByteArray();
		    try {
		    	bos.close();
		    } catch (IOException e) {}
		    
		    return ret;
	    } catch (DataFormatException e) {
	    	logger.error("DataFormatException {}",e);
	    } finally{
	    	bos = null;
	    	inflater = null;
	    }
	    
	    return new byte[0];
	}
	
	public static byte[] ipToBytes(String ip) {

		String[] ips = ip.split("\\.");
		byte[] ipBytes = new byte[4];
		if (ips.length != 4) {
			// ipaddress 总共是16字节长度
			logger.error("ip长度不符合规范" + Arrays.toString(ips));
		} else {
			for (int i = 0; i < ips.length; i++) {
				try {
					ipBytes[i] = (byte) Short.parseShort(ips[i]);
					logger.debug("ip[{}]为[{}]", i, Short.parseShort(ips[i]));
				} catch (NumberFormatException e) {
					logger.error("拼装IP时出错了");
				}
			}
		}
		return ipBytes;
	}
}
