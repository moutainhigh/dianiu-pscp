package test.edianniu.mis.tcp.pay;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;
import com.edianniu.pscp.mis.bean.request.pay.PayOrderListRequest;
import com.edianniu.pscp.mis.bean.response.pay.PayOrderListResponse;

public class PayOrderList extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(PayOrderList.class);

	

	@Test
	public void confirmPay() throws IOException {

		PayOrderListRequest request = new PayOrderListRequest();
		
		request.setUid(1383L);
		request.setToken("72948133");
		request.setType(6);
		request.setStatus(2);
		
		PayOrderListResponse response=this.sendRequest(request, 1002202, PayOrderListResponse.class);
		log.info("resp:{}",response);

	}

	
}
