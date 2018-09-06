package com.edianniu.pscp.test.mis;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.request.electricianworkorder.SettlementRequest;
import com.edianniu.pscp.mis.bean.request.user.GetUserRequest;
import com.edianniu.pscp.mis.bean.request.wallet.GetBanksRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.SettlementResponse;
import com.edianniu.pscp.mis.bean.response.user.GetUserResponse;
import com.edianniu.pscp.mis.bean.response.wallet.GetBanksResponse;

public class settle extends BaseHttp {
	private static final Logger log = LoggerFactory.getLogger(settle.class);

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

		String uri = "http://" + ServerIP + ":" + Port + "/electricianworkorder/settlement";
		SettlementRequest req = new SettlementRequest();
		req.setUid(1072L);
		req.setToken("13632624");
		req.setAmount("200.00");
		req.setOrderId("EWD10341137646096067");
		String result = httpPost(uri, req);
		log.info("result=" + result);
		SettlementResponse resp = JSONObject
				.parseObject(result, SettlementResponse.class);
		log.info("resp=" + resp);

	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown");
	}

	public static void main(String[] args) {

	}
}
