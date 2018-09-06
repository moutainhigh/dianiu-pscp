package com.edianniu.pscp.test.mis;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.request.user.GetUserCenterRequest;
import com.edianniu.pscp.mis.bean.request.user.GetUserRequest;
import com.edianniu.pscp.mis.bean.response.user.GetUserCenterResponse;
import com.edianniu.pscp.mis.bean.response.user.GetUserResponse;

public class GetUserCenter extends BaseHttp {
	private static final Logger log = LoggerFactory.getLogger(GetUserCenter.class);

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

		String uri = "http://" + ServerIP + ":" + Port + "/member/center";
		GetUserCenterRequest req = new GetUserCenterRequest();
		req.setUid(1095L);
		req.setToken("85921728");
		String result = httpPost(uri, req);
		GetUserCenterResponse resp = JSONObject
				.parseObject(result, GetUserCenterResponse.class);
		log.info("resp=" + result);

	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown");
	}

	public static void main(String[] args) {

	}
}
