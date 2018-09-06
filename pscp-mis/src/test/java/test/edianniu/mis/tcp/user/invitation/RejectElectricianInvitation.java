package test.edianniu.mis.tcp.user.invitation;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.request.user.invitation.ElectricianInvitationRequest;
import com.edianniu.pscp.mis.bean.request.user.invitation.RejectElectricianInvitationRequest;
import com.edianniu.pscp.mis.bean.response.user.invitation.ElectricianInvitationResponse;
import com.edianniu.pscp.mis.bean.response.user.invitation.RejectElectricianInvitationResponse;

public class RejectElectricianInvitation extends AbstractLocalTest {
	private static final Logger log = LoggerFactory
			.getLogger(RejectElectricianInvitation.class);

	@Test
	public void test() throws IOException {

		RejectElectricianInvitationRequest request = new RejectElectricianInvitationRequest();
		request.setUid(1241L);
		request.setToken("41191174");
		request.setInvitationId(1005L);
		RejectElectricianInvitationResponse resp = this.sendRequest(request, 1002065,
				RejectElectricianInvitationResponse.class);
		log.info("resp:{}", resp);
	}

}
