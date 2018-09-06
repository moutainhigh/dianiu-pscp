package test.edianniu.sps.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class FileUpload extends BaseHttp {
	private static final Logger log = LoggerFactory.getLogger(FileUpload.class);

	// private BeanMixedTLVDecoder tlvBeanDecoder;
	// private BeanMixedTLVEncoder tlvBeanEncoder;

	@Before
	public void setUp() throws Exception {
		/*
		 * AbstractApplicationContext root = new ClassPathXmlApplicationContext(
		 * "classpath*:stc/protocol/mixedCodec.xml"); tlvBeanDecoder =
		 * (BeanMixedTLVDecoder) root.getBean("tlvBeanDecoder"); tlvBeanEncoder
		 * = (BeanMixedTLVEncoder) root.getBean("tlvBeanEncoder");
		 */

	}

	@Test
	public void test() throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(
				"http://192.168.1.251:5013/file/upload");
		File file = new File("D:\\tmp\\20160429195635_chexian.jpg");
		FileInputStream input = new FileInputStream(file);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int inputLen = 0;
		byte[] inputBytes = new byte[10 * 1024 * 1024];
		while ((inputLen = input.read(inputBytes)) != -1) {
			output.write(inputBytes, 0, inputLen);
		}
		String base64 = Base64.encodeBase64String(output.toByteArray());
		Map<String, String> map = new HashMap<String, String>();
		map.put("file", base64);
		map.put("uid", "1034");
		map.put("token", "86849798");
		byte[] body = JSONObject.toJSONString(map).getBytes("UTF-8");
		HttpEntity requestEntity = new ByteArrayEntity(body);
		post.setEntity(requestEntity);
		post.addHeader("Connection", "Keep-Alive");
		post.addHeader("Content-Type", "application/json");
		/*post.getParams().setBooleanParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, false);*/
		CloseableHttpResponse res = httpClient.execute(post);
		InputStream input1 = res.getEntity().getContent();
		ByteArrayOutputStream output1 = new ByteArrayOutputStream();
		int len = 0;
		byte[] by = new byte[10240];
		while ((len = input1.read(by)) != -1) {
			output1.write(by, 0, len);
		}
		System.out
				.println("status code=" + res.getStatusLine().getStatusCode());
		String result = new String(output1.toByteArray());
		System.out.println("result=" + result);
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown");
	}

	public static void main(String[] args) {

	}
}
