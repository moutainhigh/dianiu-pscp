package test.edianniu.mis.tcp.pay;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.pay.OrderType;
import com.edianniu.pscp.mis.bean.pay.PayType;
import com.edianniu.pscp.mis.bean.request.pay.ConfirmPayRequest;
import com.edianniu.pscp.mis.bean.response.pay.ConfirmPayResponse;

public class ConfirmPay extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(ConfirmPay.class);

	

	@Test
	public void confirmPay() throws IOException {

		ConfirmPayRequest request = new ConfirmPayRequest();
		
		request.setUid(1061L);
		request.setToken("72948133");
		request.setResultStatus("success");
		request.setOrderId("WD1412108324012505");
		request.setPayType(PayType.WALLET.getValue());
		request.setOrderType(OrderType.SOCIAL_ELECTRICIAN_WORK_ORDER_SETTLEMENT.getValue());
		
		ConfirmPayResponse response=this.sendRequest(request, 1002060, ConfirmPayResponse.class);
		log.info("resp:{}",response);

	}

	
}
