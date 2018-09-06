package test.edianniu.mis.tcp.pay;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.pay.OrderType;
import com.edianniu.pscp.mis.bean.pay.PayType;
import com.edianniu.pscp.mis.bean.request.pay.PreparePayRequest;
import com.edianniu.pscp.mis.bean.response.pay.PreparePayResponse;

public class PreparePay extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(PreparePay.class);

	

	@Test
	public void preparePay() throws IOException {

		PreparePayRequest request = new PreparePayRequest();
		request.setUid(1061L);
		request.setToken("72948133");
		request.setOrderIds("EWD1721152942072061,EWD1721009590005061");
		request.setOrderType(OrderType.SOCIAL_ELECTRICIAN_WORK_ORDER_SETTLEMENT.getValue());
		request.setPayType(PayType.WALLET.getValue());
		request.setAmount("150.00");
		request.setClientHost("127.0.0.1");
		
		PreparePayResponse response=this.sendRequest(request, 1002059, PreparePayResponse.class);
		log.info("resp:{}",response);

	}

	
}
