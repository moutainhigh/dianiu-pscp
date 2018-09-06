package test.edianniu.sps.http;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.sps.bean.request.CheckAppUpdateRequest;
import com.edianniu.pscp.sps.bean.request.workOrder.SelectDataRequest;
import com.edianniu.pscp.sps.bean.response.CheckAppUpdateResponse;
import com.edianniu.pscp.sps.bean.response.workOrder.SelectDataResponse;

public class SelectData extends BaseHttp {
	private static final Logger log = LoggerFactory.getLogger(SelectData.class);

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

		String uri = "http://" + ServerIP + ":" + Port + "/sps/workorder/selectdata";
		SelectDataRequest req = new SelectDataRequest();
		
		String result = httpPost(uri, req);
		/*SelectDataResponse resp = JSONObject
				.parseObject(result, SelectDataResponse.class);*/
		log.info("result=" + result);
		//log.info("resp=" + resp);

	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown");
	}

	public static void main(String[] args) {

	}
}
