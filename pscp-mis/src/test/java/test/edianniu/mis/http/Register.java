package test.edianniu.mis.http;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.request.user.LoginRequest;
import com.edianniu.pscp.mis.bean.request.user.RegisterRequest;
import com.edianniu.pscp.mis.bean.response.user.LoginResponse;
import com.edianniu.pscp.mis.bean.response.user.RegisterResponse;

public class Register extends BaseHttp {
	private static final Logger log = LoggerFactory.getLogger(Register.class);

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

		String uri = "http://" + ServerIP + ":" + Port + "/member/register";
		RegisterRequest request = new RegisterRequest();
		request.setMobile("13666688420");
		request.setPasswd("123456");//71238283 64922433
		request.setMsgcode("69381");
		request.setMsgcodeid("5fb0dbc4-7ee1-4a29-9edd-49c6f03cb94b");
		String result = httpPost(uri, request);
		RegisterResponse resp = JSONObject
				.parseObject(result, RegisterResponse.class);
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
