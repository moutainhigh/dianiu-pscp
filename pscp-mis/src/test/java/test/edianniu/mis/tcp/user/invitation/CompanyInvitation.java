package test.edianniu.mis.tcp.user.invitation;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.request.user.invitation.CompanyInvitationRequest;
import com.edianniu.pscp.mis.bean.response.user.invitation.CompanyInvitationResponse;

public class CompanyInvitation extends AbstractLocalTest {
	private static final Logger log = LoggerFactory
			.getLogger(CompanyInvitation.class);

	@Test
	public void test() throws IOException {

		CompanyInvitationRequest request = new CompanyInvitationRequest();
		request.setUid(1184L);
		request.setToken("96423549");
		request.setMobile("13666688420");
		request.setCompanyName("浙江炎林广告有限公司未注册");
		CompanyInvitationResponse resp = this.sendRequest(request, 1002068,
				CompanyInvitationResponse.class);
		log.info("resp:{}", resp);
	}

}
