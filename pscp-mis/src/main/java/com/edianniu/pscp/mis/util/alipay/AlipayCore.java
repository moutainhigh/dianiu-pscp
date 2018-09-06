package com.edianniu.pscp.mis.util.alipay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/* *
 *类名：AlipayFunction
 *功能：支付宝接口公用函数类
 *详细：该类是请求、通知返回两个文件所调用的公用函数核心处理文件，不需要修改
 *版本：3.3
 *日期：2012-08-14
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayCore {

    /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }
    public static List<NameValuePair> paraFilter(List<NameValuePair> params) {

    	List<NameValuePair> result = new ArrayList<NameValuePair>();

        if (params == null || params.size() <= 0) {
            return result;
        }

        for (NameValuePair nv : params) {
            
            if (nv.getValue()== null || nv.getValue().equals("") || nv.getName().equalsIgnoreCase("sign")
                || nv.getName().equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.add(nv);
        }

        return result;
    }

    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params,boolean isPrix) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + ((isPrix==true)?"\"":"")+value+((isPrix==true)?"\"":"");
            } else {
                prestr = prestr + key + "=" + ((isPrix==true)?"\"":"")+ value +((isPrix==true)?"\"":"")+ "&";
            }
        }

        return prestr;
    }
    public static String createLinkString(List<NameValuePair> params) {

        
    	 String prestr = "";
        for (int i = 0; i < params.size(); i++) {
        	NameValuePair nv=params.get(i);
            String key = nv.getName();
            String value = nv.getValue();

            if (i == params.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" +value + "&";
            }
        }

        return prestr;
    }
   /* *//**
	 * 根据反馈回来的信息，生成签名结果
	 * 
	 * @param Params
	 *            通知返回来的参数数组
	 * @param sign
	 *            比对的签名结果
	 * @return 生成的签名结果
	 *//*
	public static boolean getSignVeryfy(Map<String, String> Params, String sign) {
		// 过滤空值、sign与sign_type参数
		Map<String, String> sParaNew = AlipayCore.paraFilter(Params);
		// 获取待签名字符串
		String preSignStr = AlipayCore.createLinkString(sParaNew,false);
		System.out.println("preSignStr="+preSignStr);
		// 获得签名验证结果
		boolean isSign = false;
		if (AlipayConfig.sign_type.equals("RSA")) {
			isSign = RSA.verify(preSignStr, sign, AlipayConfig,
					AlipayConfig.input_charset);
		}
		return isSign;
	}*/
	/*public static boolean getSignVeryfy(List<NameValuePair> params, String sign) {
		// 过滤空值、sign与sign_type参数
		List<NameValuePair> sParaNew = AlipayCore.paraFilter(params);
		// 获取待签名字符串
		String preSignStr = AlipayCore.createLinkString(sParaNew);
		System.out.println("preSignStr="+preSignStr);
		// 获得签名验证结果
		boolean isSign = false;
		if (AlipayConfig.sign_type.equals("RSA")) {
			isSign = RSA.verify(preSignStr, sign, AlipayConfig.ali_public_key,
					AlipayConfig.input_charset);
		}
		return isSign;
	}*/
	public static List<NameValuePair> parseParams2(String str) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (StringUtils.isNotBlank(str)) {
			String[] keyValues = str.split("&");
			if (keyValues != null) {
				for (String keyValue : keyValues) {
					int idx=keyValue.indexOf("=");
					if(idx>0){
						String key=keyValue.substring(0, idx);
						String value=keyValue.substring(idx+1);
						NameValuePair nv=new BasicNameValuePair(key,value);
						params.add(nv);
					}
				}
			}
		}
		return params;
	}
	public static Map<String, String> parseParams(String str) {
		Map<String, String> params = new HashMap<String, String>();
		if (StringUtils.isNotBlank(str)) {
			String[] keyValues = str.split("&");
			if (keyValues != null) {
				for (String keyValue : keyValues) {
					int idx=keyValue.indexOf("=");
					if(idx>0){
						String key=keyValue.substring(0, idx);
						String value=keyValue.substring(idx+1);
						params.put(key, value);
					}
					
					
				}
			}
		}
		return params;
	}
	public static void main(String args[]){
		Map<String, String> params=parseParams("k====aaa84884=95995=&id=1231123123");
		System.out.println(params);
	}
}
