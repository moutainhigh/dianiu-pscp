package test.edianniu.mis.tcp.socialworkorder;

import com.edianniu.pscp.mis.bean.request.socialworkorder.DetailRequest;
import com.edianniu.pscp.mis.bean.response.socialworkorder.DetailResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

public class Detail extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(Detail.class);

	

	@Test
	public void detail() throws IOException {

		DetailRequest request = new DetailRequest();
		request.setOrderId("SGD3723868807280720");
		request.setUid(1185L);
		request.setToken("31589328");
		
		DetailResponse response=this.sendRequest(request, 1002015, DetailResponse.class);
		log.info("resp:{}",response);

	}

	
}
