/**
 * 
 */
package com.edianniu.pscp.mis.util.wxpay;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import javax.net.ssl.SSLContext;

/**
 * @author cyl
 *
 */
public class WxPayUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(WxPayUtil.class);
	public static String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	public static String getTimeStamp(){
		return String.valueOf(System.currentTimeMillis() / 1000);
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
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }
    public static boolean getSignVeryfy(List<NameValuePair> params, String sign,String apiKey) {
    	String packageSign=genAppSign(params,apiKey);
    	if(packageSign.equals(sign)){
    		return true;
    	}
    	return false;
    }
	/**
	 * 生成签名
	 */
	public static String genPackageSign(List<NameValuePair> params,String apiKey) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			if(StringUtils.isNoneBlank(params.get(i).getValue())){
				sb.append(params.get(i).getName());
				sb.append('=');
				sb.append(params.get(i).getValue());
				sb.append('&');
			}
		}
		sb.append("key=");
		sb.append(apiKey);


		String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		return packageSign;
	}
	public static String genAppSign(List<NameValuePair> params,String apiKey) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(apiKey);
		String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		return appSign;
	}
	public static String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<"+params.get(i).getName()+">");


			sb.append(params.get(i).getValue());
			sb.append("</"+params.get(i).getName()+">");
		}
		sb.append("</xml>");
		return sb.toString();
	}
	public static Map<String,String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParserFactory pullParserFactory=XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName=parser.getName();
				switch (event) {
					case XmlPullParser.START_DOCUMENT:

						break;
					case XmlPullParser.START_TAG:

						if("xml".equals(nodeName)==false){
							//实例化student对象
							xml.put(nodeName,parser.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
			logger.error("decodeXml:{}",e);
		}
		return null;

	}




	/**
	 * 发送请求给微信. 请传入已经签过名的参数.
	 * @param url
	 * @param params 包含签名的请求参数.
	 * @param mchId 商户号
	 * @param certDirPath 证书目录
	 * @return 微信的返回结果.
	 * @throws KeyStoreException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 */
	public static Map<String, String> sendRequest(String url, List<NameValuePair> params,String mchId,String certDirPath) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, KeyManagementException, UnrecoverableKeyException {
		String mch_id =mchId;
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		InputStream is =  new FileInputStream(new File(certDirPath+"/"+mchId+".p12"));
		try {
			keyStore.load(is, mch_id.toCharArray());
		} finally {
			is.close();
		}
		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom()
				.loadKeyMaterial(keyStore, mch_id.toCharArray())
				.build();
		// Allow TLSv1 protocol only
		@SuppressWarnings("deprecated")
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext,
				new String[]{"TLSv1"},
				null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		HttpPost post = new HttpPost(url);

		String xmlstring = WxPayUtil.toXml(params);
		StringEntity stringEntity = new StringEntity(xmlstring,
				ContentType.create("text/plain", Charset.forName("utf-8")));

		post.setEntity(stringEntity);
		post.addHeader("Connection", "Keep-Alive");
		post.addHeader("Accept", "application/json");
		post.addHeader("Content-type", "application/json");
		// post.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE,
		// false);
		CloseableHttpResponse res = httpClient.execute(post);
		InputStream input = res.getEntity().getContent();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int len = 0;
		byte[] by = new byte[1024];
		while ((len = input.read(by)) != -1) {
			output.write(by, 0, len);
		}
		Header[] headers = res.getAllHeaders();
		int statusCode = res.getStatusLine().getStatusCode();
		logger.info("weixin return  statusCode:" + statusCode);
		for (Header h : headers) {
			logger.info(h.toString());
		}
		String content = new String(output.toByteArray());
		return WxPayUtil.decodeXml(content);
	}
	public static Map<String, String> sendRequest(String url, List<NameValuePair> params) throws ClientProtocolException,IOException,Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		String xmlstring = WxPayUtil.toXml(params);
		StringEntity stringEntity = new StringEntity(xmlstring,
				ContentType.create("text/plain", Charset.forName("utf-8")));

		post.setEntity(stringEntity);
		post.addHeader("Connection", "Keep-Alive");
		post.addHeader("Accept", "application/json");
		post.addHeader("Content-type", "application/json");
		// post.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE,
		// false);
		CloseableHttpResponse res = httpClient.execute(post);
		InputStream input = res.getEntity().getContent();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int len = 0;
		byte[] by = new byte[1024];
		while ((len = input.read(by)) != -1) {
			output.write(by, 0, len);
		}
		Header[] headers = res.getAllHeaders();
		int statusCode = res.getStatusLine().getStatusCode();
		logger.info("weixin return  statusCode:" + statusCode);
		for (Header h : headers) {
			logger.info(h.toString());
		}
		String content = new String(output.toByteArray());
		return WxPayUtil.decodeXml(content);
	}
}
