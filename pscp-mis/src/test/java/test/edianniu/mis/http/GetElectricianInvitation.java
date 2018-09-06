package test.edianniu.mis.http;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.request.user.GetUserRequest;
import com.edianniu.pscp.mis.bean.request.user.invitation.GetElectricianInvitationRequest;
import com.edianniu.pscp.mis.bean.response.user.GetUserResponse;
import com.edianniu.pscp.mis.bean.response.user.invitation.GetElectricianInvitationResponse;

public class GetElectricianInvitation extends BaseHttp {
	private static final Logger log = LoggerFactory.getLogger(GetElectricianInvitation.class);

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

		String uri = "http://" + ServerIP + ":" + Port + "/member/electrician/getinvitation";
		GetElectricianInvitationRequest request = new GetElectricianInvitationRequest();
		request.setUid(1184L);
		request.setToken("76536325");
		String result = httpPost(uri, request);
		GetElectricianInvitationResponse resp = JSONObject
				.parseObject(result, GetElectricianInvitationResponse.class);
		log.info("resp:{}", resp);

	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown");
	}

	public static void main(String[] args) {

	}
}
