package test.edianniu.sps.http.socialworkorder;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.sps.http.BaseHttp;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.sps.bean.request.HomeRequest;
import com.edianniu.pscp.sps.bean.request.socialworkorder.ListRequest;
import com.edianniu.pscp.sps.bean.response.HomeResponse;
import com.edianniu.pscp.sps.bean.response.socialworkorder.ListResponse;

public class List extends BaseHttp {
	private static final Logger log = LoggerFactory.getLogger(List.class);

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

		String uri = "http://" + ServerIP + ":" + Port + "/sps/socialworkorder/list";
		ListRequest req = new ListRequest();
		req.setUid(1184L);
		req.setStatus("recruiting");
		req.setToken("41439513");
		//req.setAppPkg("com.edianniu.pscp.electrician");
		String result = httpPost(uri, req);
		ListResponse resp = JSONObject
				.parseObject(result, ListResponse.class);
		log.info("resp=,result=" + resp,result);

	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown");
	}

	public static void main(String[] args) {

	}
}
