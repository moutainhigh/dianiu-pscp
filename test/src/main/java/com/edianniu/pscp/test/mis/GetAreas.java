package com.edianniu.pscp.test.mis;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.request.GetAreasRequest;
import com.edianniu.pscp.mis.bean.request.GetCitysRequest;
import com.edianniu.pscp.mis.bean.request.user.GetUserRequest;
import com.edianniu.pscp.mis.bean.response.GetAreasResponse;
import com.edianniu.pscp.mis.bean.response.GetCitysResponse;
import com.edianniu.pscp.mis.bean.response.user.GetUserResponse;

public class GetAreas extends BaseHttp {
	private static final Logger log = LoggerFactory.getLogger(GetAreas.class);

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

		String uri = "http://" + ServerIP + ":" + Port + "/mis/getareas";
		GetAreasRequest req = new GetAreasRequest();
		req.setUid(1054L);
		req.setToken("73841782");
		req.setCityId(206L);
		String result = httpPost(uri, req);
		GetAreasResponse resp = JSONObject
				.parseObject(result,GetAreasResponse.class);
		log.info("resp=" + resp);

	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown");
	}

	public static void main(String[] args) {

	}
}
