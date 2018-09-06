/**
 * 
 */
package test.edianniu.renter.mis.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreProtocolPNames;
import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.renter.mis.bean.request.BaseRequest;

/**
 * @author elliot.chen
 *
 */
public abstract class BaseHttp {
	protected String httpPost(String uri,
			BaseRequest req) throws UnsupportedEncodingException,
			IOException, ClientProtocolException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		byte[] body = JSONObject.toJSONString(req).getBytes("UTF-8");
		HttpPost post = new HttpPost(uri);
		HttpEntity requestEntity = new ByteArrayEntity(body);
		post.setEntity(requestEntity);
		post.addHeader("Connection", "Keep-Alive");
		post.addHeader("Content-Type", "application/json");
		post.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
		CloseableHttpResponse res = httpClient.execute(post);
		InputStream input = res.getEntity().getContent();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int len = 0;
		byte[] by = new byte[1024];
		while ((len = input.read(by)) != -1) {
			output.write(by, 0, len);
		}
		System.out.println("status code="+res.getStatusLine().getStatusCode());
		String result=new String(output.toByteArray(),"utf-8");
		return result;
	}
	//public static String ServerIP = "api.edianniu.com"; 
	public static String ServerIP = "192.168.1.251"; 
	public static int Port = 5004; // 未加密
	//public static int Port = 80; // 未加密
	
	
}
