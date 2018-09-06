package test.edianniu.mis.tcp.socialworkorder;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.request.socialworkorder.HomeRequest;
import com.edianniu.pscp.mis.bean.response.socialworkorder.HomeResponse;

public class Home extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(Home.class);

	

	@Test
	public void home() throws IOException {

		HomeRequest request = new HomeRequest();
		request.setUid(1034L);
		request.setToken("65788394");
		HomeResponse resp=this.sendRequest(request, 1002013,HomeResponse.class);
		log.debug("resp:",resp);
		

		

	}



}
