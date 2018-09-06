package test.edianniu.mis.tcp.user.invitation;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.request.user.invitation.ConfirmCompanyInvitationRequest;
import com.edianniu.pscp.mis.bean.response.user.invitation.ConfirmCompanyInvitationResponse;

public class ConfirmCompanyInvitation extends AbstractLocalTest {
	private static final Logger log = LoggerFactory
			.getLogger(ConfirmCompanyInvitation.class);

	@Test
	public void test() throws IOException {

		ConfirmCompanyInvitationRequest request = new ConfirmCompanyInvitationRequest();
		request.setUid(1243L);
		request.setToken("23461938");
		request.setInvitationId(1052L);
		request.setActionType("reject");
		ConfirmCompanyInvitationResponse resp = this.sendRequest(request, 1002069,
				ConfirmCompanyInvitationResponse.class);
		log.info("resp:{}", resp);
	}

}
