/**
 * @author yanlin.chen  yanlin.chen@edianniu.com
 * @version V1.0 2018年4月24日 下午3:46:19 
 */
package com.edianniu.pscp.mis.util;

import java.util.Date;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月24日 下午3:46:19 
 * @version V1.0
 */
public class DTL645V2007 {
	/**
	 * 获取命令
	 * @param address
	 * @param control
	 * @param pa
	 * @param operationCode
	 * @param n1
	 * @return
	 */
	public static String getCMD(String address,String control,String pa,String operationCode,String n1){
		String cmd="";//前导字符FEFEFEFE
		cmd=cmd+"68"+formatAddress(address)+"68"+control;//12345678
		String data=pa+"00"+"00"+"00"+operationCode;
		Date date=DateUtils.getDateOffsetMinute(new Date(), 5);
		String ssmmHHddMMyy=DateUtils.format(date, "ssmmHHddMMyy");
		
		data=data+n1+"00"+"00"+ssmmHHddMMyy;
		cmd=cmd+DTL645V2007.formatHex(data.length()/2);//计算长度
		cmd=cmd+encodeData(data);
		cmd=cmd+DTL645V2007.getCs(cmd);//计算校验码
		cmd=cmd+"16";
		return cmd;
	}
	public static String formatAddress(String address){
		String s="";//00001122
		for(int i=0;i<address.length();){
			String tmp=address.substring(i, i+2);
			s=tmp+s;
			i=i+2;
		}
		return s;
	}
	/**
	 * 数据域每个字节加33H
	 * @param data
	 * @return
	 */
	public static String encodeData(String data){
		StringBuffer sb=new StringBuffer(data.length());
		for(int i=0;i<data.length();){
			int v=Integer.parseInt(data.substring(i, i+2), 16);
			v=v+Integer.parseInt("33",16);
			sb.append(formatHex(v));
			i=i+2;
		}
		return sb.toString();
	}
	public static String getOperationCode(Integer code){
		String hexStr=formatHex(code);
		if(hexStr.length()>8){
			throw new RuntimeException("操作代码长度为4个字节");
		}
		int length=hexStr.length();
		if(length<8){
			for(int i=0;i<8-length;i++){
				hexStr="0"+hexStr;
			}
		}
		return hexStr;
		
	}
	public static String getCmdCutOffTime(String dateStr){
		StringBuffer sb=new StringBuffer(12);
		sb.append(formatHex(dateStr.substring(0, 2)));
		sb.append(formatHex(dateStr.substring(2, 4)));
		sb.append(formatHex(dateStr.substring(4, 6)));
		sb.append(formatHex(dateStr.substring(6, 8)));
		sb.append(formatHex(dateStr.substring(8, 10)));
		sb.append(formatHex(dateStr.substring(10, 12)));
		return sb.toString();
	}
	public static String getCs(String hexStr){
		int total=0;
		for(int i=0;i<hexStr.length();){
			int v=Integer.parseInt(hexStr.substring(i, i+2), 16);
			total=total+v;
			i=i+2;
		}
		return formatHex(total%256);
	}
	public static String formatHex(String str){
		String s=Integer.toHexString(Integer.parseInt(str)).toUpperCase();
		  s = String.format("%2s",s);
	      s= s.replaceAll(" ","0");
	    return s;
	}
	public static String formatHex(Integer i){
		String s=Integer.toHexString(i).toUpperCase();
		  s = String.format("%2s",s);
	      s= s.replaceAll(" ","0");
	    return s;
	}
	public static void main(String args[]){
		//681848164900006804066EF3338967456216
		System.out.println(DTL645V2007.getCs("68010000000000681C1035333333333333334D333333336445CC"));
		System.out.println(formatHex(12345678));
	}
}
