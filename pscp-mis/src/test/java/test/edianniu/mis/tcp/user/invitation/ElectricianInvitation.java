package test.edianniu.mis.tcp.user.invitation;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.request.user.invitation.ElectricianInvitationRequest;
import com.edianniu.pscp.mis.bean.response.user.invitation.ElectricianInvitationResponse;

public class ElectricianInvitation extends AbstractLocalTest {
	private static final Logger log = LoggerFactory
			.getLogger(ElectricianInvitation.class);

	@Test
	public void test() throws IOException {

		ElectricianInvitationRequest request = new ElectricianInvitationRequest();
		request.setUid(1184L);
		request.setToken("58358448");
		request.setMobile("13666688433");
		request.setUserName("陈电工33-04");
		ElectricianInvitationResponse resp = this.sendRequest(request, 1002063,
				ElectricianInvitationResponse.class);
		log.info("resp:{}", resp);
	}

}
