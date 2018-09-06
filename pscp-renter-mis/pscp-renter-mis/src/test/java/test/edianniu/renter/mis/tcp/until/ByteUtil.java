package test.edianniu.renter.mis.tcp.until;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
* @ClassName: ByteUtil 
* @Description: Byte字节流相关的 处理方法。
* @author Cay.Jiang   
* @date 2011-7-5 下午04:19:34 
*  
*/ 
public class ByteUtil {
	private static final Log log = LogFactory.getLog(ByteUtil.class);
	private static int tag_byteNum=4;
	private static int len_byteNum=4;

	public static void setTag_byteNum(int tag_byteNum) {
		ByteUtil.tag_byteNum = tag_byteNum;
	}
	public static void setLen_byteNum(int len_byteNum) {
		ByteUtil.len_byteNum = len_byteNum;
	}

	//把byte[]型转换成TLV方式的字节数组流
	public static byte[] bytes2TLVbytes(int tag,byte[] value){
		byte[] btag=int2bytes(tag,tag_byteNum);
		byte[] blength=int2bytes(value.length,len_byteNum);
		byte[] buff=pack(btag,blength,value);
		return buff;
	}
	//把整型转换成指定长度的数组字节流,默认4个字节
	public static byte[] int2bytes(int integer) {
//	   if (integer < 0) { throw new IllegalArgumentException("Can not cast negative to bytes : " + integer); }
	   ByteArrayOutputStream bo = new ByteArrayOutputStream();
	   for (int i = 0; i < 4; i++) {
	    bo.write(integer);
	    integer = integer >> 8;
	   }
	   byte[] res=reversEndian(bo.toByteArray(),4);
	   return res;
	}
	//把整型转换成指定长度的数组字节流，可指定字节长度
	public static byte[] int2bytes(int integer, int len) {
//	   if (integer < 0) { throw new IllegalArgumentException("Can not cast negative to bytes : " + integer); }
	   ByteArrayOutputStream bo = new ByteArrayOutputStream();
	   for (int i = 0; i < len; i++) {
	    bo.write(integer);
	    integer = integer >> 8;
	   }
	   byte[] res=reversEndian(bo.toByteArray(),len);
	   return res;
	}
	//把整型转换成TLV方式的字节数组流，默认4个字节
	public static byte[] int2TLVbytes(int tag,int value){
		byte[] tag1=int2bytes(tag,tag_byteNum);
		byte[] length1=int2bytes(4,len_byteNum);
		byte[] value1=int2bytes(value,4);
		byte[] buff=pack(tag1,length1,value1);
		return buff;
	}
	//把整型转换成TLV方式的字节数组流，可指定字节长度
	public static byte[] int2TLVbytes(int tag,int value,int len){
		byte[] tag1=int2bytes(tag,tag_byteNum);
		byte[] length1=int2bytes(len,len_byteNum);
		byte[] value1=int2bytes(value,len);
		byte[] buff=pack(tag1,length1,value1);
		return buff;
	}
	//把长整型转换成指定长度的数组字节流
	public static byte[] long2bytes(long value) { 
		 byte[] b=new byte[8];
		 for(int i=0;i<8;i++){
			 b[i]=(byte)(value>>(56-i*8));
		 }
		 return b;
	} 
	
	//把长整型转换成TLV方式的字节数组流
	public static byte[] long2TLVbytes(int tag,long value){
		byte[] tag1=ByteUtil.int2bytes(tag,tag_byteNum);
		byte[] length1=ByteUtil.int2bytes(8,len_byteNum);
		byte[] value1=long2bytes(value);
		byte[] buff=ByteUtil.pack(tag1,length1,value1);
		return buff;
	}
	// 把UTF-8的string转换成指定长度的字节数组流，不足部分补0x00
	public static byte[] UTF8string2bytes(String source, int length) {
		return UTF8string2bytes(source, length,(byte)(0x00));
	}
	// 把UTF-8的string转换成指定长度的字节数组流，不足部分补fillByte
	public static byte[] UTF8string2bytes(String source, int length,byte fillByte) {
		byte[] dst = new byte[length];
		if (source == null) {
			for (int i = 0; i < length; i++) {
				dst[i] = fillByte;
			}
			return dst;
		}
		try {
			byte[] b = source.getBytes("UTF-8");
			if (b.length > length) {
				System.arraycopy(b, 0, dst, 0, length);
			} else {
				System.arraycopy(b, 0, dst, 0, b.length);
				for (int i = dst.length; i < length; i++) {
					dst[i] = fillByte;
				}
			}
		} catch (Exception e) {
			for (int i = 0; i < length; i++) {
				dst[i] = fillByte;
			}
		}
		return dst;
	}
	//把string转换成TLV编码方式的字节数组流
	public static byte[] UTF8string2TLVbytes(int tag,String value){
		if(value==null){
			return null;
		}
			byte[] tag1=int2bytes(tag,tag_byteNum);
			byte[] length1=null;
			byte[] value1=null;
			try {
				int length=value.getBytes("UTF-8").length;
				length1=int2bytes(length,len_byteNum);
				value1 = UTF8string2bytes(value,length,(byte)0x00);
			} catch (UnsupportedEncodingException e) {
				System.out.println("UnsupportedEncodingException");
				e.printStackTrace();
			}
			byte[] buff=pack(tag1,length1,value1);
			return buff;
	}
	
	
	//字节数据转换成int整型
	public static int bytes2int(byte b[]) {
		int s = 0;
		for (int i = 0; i < b.length; i++) {
			s = s | ((b[i] & 0xff) << ((b.length - i - 1) * 8));
		}
		return s;
	}
	
	//将字节流中的指定字节段转换成int整型
	public static int bytes2int(byte b[],int offset,int len){
		byte[] respcode=new byte[len];
		for (int i=0;i<len;i++){
			respcode[i]=b[offset];
			offset++;
		}
		return bytes2int(respcode);
	}
	//将字节流中的指定字节段转换成long整型
	public static long bytes2long(byte[] b){ 
	  long res=0;
	  for(int i=0;i<8;i++){
	  res<<=8;
	  res|=b[i]&0xff;
	  }
	  return res;
	  }
	//将字节流中的指定字节段转换成long整型
	public static long bytes2long(byte b[],int offset,int len){
		byte[] respcode=new byte[len];
		for (int i=0;i<len;i++){
			respcode[i]=b[offset];
			offset++;
		}
		return bytes2long(respcode);
	}
	//把字节转换成UTF-8字符串
	public static String bytes2UTF8string(byte source[]) {
		if (source==null){
			return null;
		}
		String dst = "";
		try {
			dst = (new String(source, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			dst = "";
		}
		return dst;
	}
	//将字节流中的指定字节段转换成UTF-8字符型
	public static String bytes2UTF8string(byte b[],int offset,int len){
		byte[] a=new byte[len];
		for (int i=0;i<len;i++){
			a[i]=b[offset];
			offset++;
		}
		return bytes2UTF8string(a);
	}
	//将不同的byte[]字节数组流打包成一个字节数组
	public static byte[] pack(byte[]... agrs) {
	    ByteArrayOutputStream bout = new ByteArrayOutputStream();
	    for(byte[] b:agrs){
	    	if(b!=null){
	    		try {
					bout.write(b);
				} catch (IOException e) {
					System.out.println("IOException");
					e.printStackTrace();
				}
	    	}
	    	continue;
	    }
	    byte[] buff = bout.toByteArray();
	    return buff;
	}
	
	//将byte[]列表 打包成一个字节数组
	public static byte[] pack(List<byte[]> agrs) {
	    ByteArrayOutputStream bout = new ByteArrayOutputStream();
	    for(byte[] b:agrs){
	    	if(b!=null){
	    		try {
					bout.write(b);
				} catch (IOException e) {
					System.out.println("IOException");
					e.printStackTrace();
				}
	    	}
	    	continue;
	    }
	    byte[] buff = bout.toByteArray();
	    return buff;
	}
	
    /**取到字节流data中指定字节流pattern的位置（第一次出现的后标位置）
     * The Knuth-Morris-Pratt Pattern Matching Algorithm can be used to search a byte array.
     * Search the data byte array for the first occurrence
     * of the byte array pattern.
     */
	public static int indexOf(byte[] data, byte[] pattern) {
        int[] failure = computeFailure(pattern);
        int j = 0;
        for (int i = 0; i < data.length; i++) {
            while (j > 0 && pattern[j] != data[i]) {
                j = failure[j - 1];
            }
            if (pattern[j] == data[i]) {
                j++;
            }
            if (j == pattern.length) {
            	return i + 1;
            	//return i - pattern.length + 1;
            }
        }
        return -1;
    }
	
	/** 
	* <p>Title:indexOf </p> 
	* <p>Description:取到字节流data中指定字节流pattern的位置（第n次出现的位置） </p> 
	* @param data	源数据流
	* @param pattern	指定搜索的数据流
	* @param n		第几次出现的位置
	* @return 	指定数据流第n次出现的位置（后标）
	*/ 
	public static int indexOf(byte[] data, byte[] pattern,int n){
	     int length=0;
		 int temp=0;
	     if(n<=1){
		      length= indexOf(data,pattern);
		     }
	     else{
		 for (int j=0;j<n;j++){
	       temp=indexOf(data,pattern);
	       if(temp>0){
	       data=copyBytes(data, temp, data.length-temp);
	       length=length+temp;
	       }
	       else  {
	    	   return -1;
	       }
	     }
	     }
		 return length;
	 }
	//判断data字节数组中是否存在pattern
	public static int isexist(byte[] data, byte[] pattern) {
        int[] failure = computeFailure(pattern);
        int j = 0;
        for (int i = 0; i < data.length; i++) {
            while (j > 0 && pattern[j] != data[i]) {
                j = failure[j - 1];
            }
            if (pattern[j] == data[i]) {
                j++;
            }
            if (j == pattern.length) {
            	return 1;
            	//return i - pattern.length + 1;
            }
        }
        return -1;
    }
	//HEX字节流以String方式显示
	public static String dumpBytesAsHEX(byte[] bytes) {
		int idx = 0;
		String s = "";
		StringBuilder body = new StringBuilder();
		for (int i=0;i<1024&&i<bytes.length;i++) {
			byte b = bytes[i];
			int hex = ((int) b) & 0xff;
			String shex = Integer.toHexString(hex).toUpperCase();
			if (1 == shex.length()) {
				body.append("0");
			}
			body.append(shex);
			body.append(" ");
			idx++;
//			if (16 == idx) {
//				s = body.toString();
//				body = new StringBuilder();
//				idx = 0;
//			}
		}
		if (idx != 0) {
			s = body.toString();
		}
		return s;
	}
	//TLV字节流中从resp中获取指定tag的value(byte[])
	public static byte[] getTLVBytes(byte[] resp,int tag){
		byte[] btag=int2bytes(tag, tag_byteNum);
		int index=indexOf(resp,btag);
		if (index==-1){
			log.warn("No data found for the 1 occurrence in tag "+tag);
			return null;
		}
		int length=bytes2int(resp,index,len_byteNum);
		byte[] value=copyBytes(resp,index+len_byteNum,length);
		
		return value;
	}
	//按照tag出现的次数n，TLV字节流中从resp中获取指定tag的value(byte[])
	public static byte[] getTLVBytes(byte[] resp,int tag,int n){
		byte[] btag=int2bytes(tag, tag_byteNum);
		int index=indexOf(resp,btag,n);
		if (index==-1){
			log.warn("No data found for the "+n+" occurrence in tag "+tag);
			return null;
		}
		int length=bytes2int(resp,index,len_byteNum);
		byte[] value=copyBytes(resp,index+len_byteNum,length);
		return value;
	}
	//TLV字节流中从resp中获取指定tag的value(int)
	public static int getTLVInt(byte[] resp,int tag){
		byte[] btag=int2bytes(tag, tag_byteNum);
		int index=indexOf(resp,btag);
		if (index==-1){
			log.warn("No data found for the 1 occurrence in tag "+tag);
			return -1;
		}
		int length=bytes2int(resp,index,len_byteNum);
		int value=bytes2int(resp,index+len_byteNum,length);
		return value;
	}
	//按照tag出现的次数n，TLV字节流中从resp中获取指定tag的value(int)
	public static int getTLVInt(byte[] resp,int tag,int n){
		byte[] btag=int2bytes(tag, tag_byteNum);
		int index=indexOf(resp,btag,n);
		if (index==-1){
			log.warn("No data found for the "+n+" occurrence in tag "+tag);
			return -1;
		}
		int length=bytes2int(resp,index,len_byteNum);
		int value=bytes2int(resp,index+len_byteNum,length);
		return value;
	}
	//TLV字节流中从resp中获取指定tag的value(long)
	public static long getTLVLong(byte[] resp,int tag){
		byte[] btag=int2bytes(tag, tag_byteNum);
		int index=indexOf(resp,btag);
		if (index==-1){
			log.warn("No data found for the 1 occurrence in tag "+tag);
			return -1;
		}
		int length=bytes2int(resp,index,len_byteNum);
		long value=bytes2long(resp,index+len_byteNum,length);
		return value;
	}
	//TLV字节流中从resp中获取指定tag的value(long)
	public static long getTLVLong(byte[] resp,int tag,int n){
		byte[] btag=int2bytes(tag, tag_byteNum);
		int index=indexOf(resp,btag,n);
		if (index==-1){
			log.warn("No data found for the 1 occurrence in tag "+tag);
			return -1;
		}
		int length=bytes2int(resp,index,len_byteNum);
		long value=bytes2long(resp,index+len_byteNum,length);
		return value;
	}
	//TLV字节流中从resp中获取指定tag的value(string)
	public static String getTLVString(byte[] resp,int tag){
		byte[] btag=int2bytes(tag, tag_byteNum);
		int index=indexOf(resp,btag);
		if (index==-1){
			log.warn("No data found for the 1 occurrence in tag "+tag);
			return null;
		}
		int length=bytes2int(resp,index,len_byteNum);
		String value=bytes2UTF8string(resp,index+len_byteNum,length);
		return value;
	}
	//按照tag出现的次数n，TLV字节流中从resp中获取指定tag的value(string)
	public static String getTLVString(byte[] resp,int tag,int n){
		byte[] btag=int2bytes(tag, tag_byteNum);
		int index=indexOf(resp,btag,n);
		if (index==-1){
			log.warn("No data found for the "+n+" occurrence in tag "+tag);
			return null;
		}
		int length=bytes2int(resp,index,len_byteNum);
		String value=bytes2UTF8string(resp,index+len_byteNum,length);
		return value;
	}
	
	//copy指定位置和长度的字节数组到新的字节数组
	public static byte[] copyBytes(byte[] b1,int start,int length){
	      byte[] ret = new byte[length];
	      if (start + length > b1.length) return null;      
	      System.arraycopy(b1, start, ret, 0, length);
	      return ret;
	     }
    /**
     * Computes the failure function using a boot-strapping process,
     * where the pattern is matched against itself.
     */
    private static int[] computeFailure(byte[] pattern) {
        int[] failure = new int[pattern.length];
        int j = 0;
        for (int i = 1; i < pattern.length; i++) {
            while (j>0 && pattern[j] != pattern[i]) {
                j = failure[j - 1];
            }
            if (pattern[j] == pattern[i]) {
                j++;
            }
            failure[i] = j;
        }
        return failure;
    }
	private static byte[] reversEndian(byte str[],int len)	//字节流翻转
	    {
	       byte b;
	       byte res[]=new byte[len];
	           for(int i=0;i<len;i++)
	           {
	              b=str[i];
	              res[len-i-1]=b;             
	           }
	       return res;
	    }
	
}