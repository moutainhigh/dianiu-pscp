package test.edianniu.mis.tcp.socialworkorder;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.request.socialworkorder.TakeRequest;
import com.edianniu.pscp.mis.bean.response.socialworkorder.TakeResponse;

public class Take extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(Take.class);



	@Test
	public void take() throws IOException {

		TakeRequest request = new TakeRequest();
		request.setUid(1034L);
		request.setToken("65788394");
		request.setOrderId("SD0000000002");
		TakeResponse resp = this.sendRequest(request, 1002016, TakeResponse.class);
		log.info("resp:{}",resp);
	}

	

}
