package com.edianniu.pscp.test.mis;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.request.GetProvincesRequest;
import com.edianniu.pscp.mis.bean.response.GetProvincesResponse;

public class GetProvinces extends BaseHttp {
	private static final Logger log = LoggerFactory.getLogger(GetProvinces.class);

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

		String uri = "http://" + ServerIP + ":" + Port + "/mis/getprovinces";
		GetProvincesRequest req = new GetProvincesRequest();
		req.setUid(1054L);
		req.setToken("73841782");
		String result = httpPost(uri, req);
		GetProvincesResponse resp = JSONObject
				.parseObject(result, GetProvincesResponse.class);
		log.info("resp=" + resp);

	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown");
	}

	public static void main(String[] args) {

	}
}
