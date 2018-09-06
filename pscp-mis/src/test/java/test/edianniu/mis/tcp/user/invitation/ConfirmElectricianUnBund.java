package test.edianniu.mis.tcp.user.invitation;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.request.user.invitation.ConfirmElectricianUnBindRequest;
import com.edianniu.pscp.mis.bean.request.user.invitation.ElectricianInvitationRequest;
import com.edianniu.pscp.mis.bean.response.user.invitation.ConfirmElectricianUnBindResponse;
import com.edianniu.pscp.mis.bean.response.user.invitation.ElectricianInvitationResponse;

public class ConfirmElectricianUnBund extends AbstractLocalTest {
	private static final Logger log = LoggerFactory
			.getLogger(ConfirmElectricianUnBund.class);

	@Test
	public void test() throws IOException {

		ConfirmElectricianUnBindRequest request = new ConfirmElectricianUnBindRequest();
		//request.setUid(1184L);
		//request.setToken("58358448");
		request.setUid(1241L);//1241
		request.setToken("41191174");//41191174
		request.setInvitationId(1005L);
		request.setActionType("agree");
		ConfirmElectricianUnBindResponse resp = this.sendRequest(request, 1002067,
				ConfirmElectricianUnBindResponse.class);
		log.info("resp:{}", resp);
	}

}
