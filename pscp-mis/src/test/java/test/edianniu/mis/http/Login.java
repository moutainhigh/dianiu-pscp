package test.edianniu.mis.http;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.request.user.LoginRequest;
import com.edianniu.pscp.mis.bean.response.user.LoginResponse;

public class Login extends BaseHttp {
	private static final Logger log = LoggerFactory.getLogger(Login.class);

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

		String uri = "http://" + ServerIP + ":" + Port + "/member/login";
		LoginRequest req = new LoginRequest();
		req.setLoginName("13666688421");//64993166 1095 1184(41439513)
		req.setPasswd("123456");
		req.setClientId("测试0001");
		req.setDeviceToken("xxxxx");
		req.setDid("xxxxx");
		req.setOsType("ios");
		req.setOsVersion("3333");
		req.setPbrand("ipnone");
		req.setPtype("222");
		req.setRamSize("222");
		req.setRomSize("222");
		req.setScreenHeight("22");
		req.setScreenWidth("222");
		req.setAppPkg("com.edianniu.pscp.facilitator");
		String result = httpPost(uri, req);
		LoginResponse resp = JSONObject
				.parseObject(result, LoginResponse.class);
		log.info("result=" + result);
		log.info("resp=" + resp);

	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown");
	}

	public static void main(String[] args) {

	}
}
