package test.edianniu.mis.tcp.user.invitation;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.request.user.invitation.ApplyElectricianUnBindRequest;
import com.edianniu.pscp.mis.bean.response.user.invitation.ApplyElectricianUnBindResponse;

public class ApplyElectricianUnBund extends AbstractLocalTest {
	private static final Logger log = LoggerFactory
			.getLogger(ApplyElectricianUnBund.class);

	@Test
	public void test() throws IOException {

		ApplyElectricianUnBindRequest request = new ApplyElectricianUnBindRequest();
		//request.setUid(1241L);//1241
		//request.setToken("41191174");//41191174
		request.setUid(1184L);
		request.setToken("58358448");
		request.setInvitationId(1005L);
		ApplyElectricianUnBindResponse resp = this.sendRequest(request, 1002066,
				ApplyElectricianUnBindResponse.class);
		log.info("resp:{}", resp);
	}

}
