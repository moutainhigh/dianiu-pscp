package test.edianniu.mis.tcp.pay;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.pay.OrderType;
import com.edianniu.pscp.mis.bean.request.pay.StartPayRequest;
import com.edianniu.pscp.mis.bean.response.pay.StartPayResponse;

public class StartPay extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(StartPay.class);

	

	@Test
	public void startPay() throws IOException {

		StartPayRequest request = new StartPayRequest();
		
		request.setUid(1061L);
		request.setToken("72948133");
		request.setOrderIds("EWD1721152942072061,EWD1721009590005061");
		request.setOrderType(OrderType.SOCIAL_ELECTRICIAN_WORK_ORDER_SETTLEMENT.getValue());
		
		StartPayResponse response=this.sendRequest(request, 1002061, StartPayResponse.class);
		log.info("resp:{}",response);

	}

	
}
