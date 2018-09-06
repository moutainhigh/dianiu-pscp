package test.edianniu.mis.tcp.user.invitation;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.request.user.invitation.GetElectricianInvitationRequest;
import com.edianniu.pscp.mis.bean.response.user.invitation.GetElectricianInvitationResponse;

public class GetElectricianInvitation extends AbstractLocalTest {
	private static final Logger log = LoggerFactory
			.getLogger(GetElectricianInvitation.class);

	@Test
	public void test() throws IOException {

		GetElectricianInvitationRequest request = new GetElectricianInvitationRequest();
		request.setUid(1184L);
		request.setToken("54291246");
		GetElectricianInvitationResponse resp = this.sendRequest(request, 1002070,
				GetElectricianInvitationResponse.class);
		log.info("resp:{}", resp);
	}

}
