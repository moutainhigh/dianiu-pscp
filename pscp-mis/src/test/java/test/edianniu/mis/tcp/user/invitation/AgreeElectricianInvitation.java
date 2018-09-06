package test.edianniu.mis.tcp.user.invitation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.electrician.CertificateImgInfo;
import com.edianniu.pscp.mis.bean.request.user.invitation.AgreeElectricianInvitationRequest;
import com.edianniu.pscp.mis.bean.response.user.invitation.AgreeElectricianInvitationResponse;

public class AgreeElectricianInvitation extends AbstractLocalTest {
	private static final Logger log = LoggerFactory
			.getLogger(AgreeElectricianInvitation.class);

	@Test
	public void test() throws IOException {

		AgreeElectricianInvitationRequest request = new AgreeElectricianInvitationRequest();
		request.setUid(1241L);
		request.setToken("41191174");
		request.setInvitationId(1005L);
		request.setUserName("电工-2012");
		request.setAppPkg("");
		request.setIdCardBackImg("idcardbackimg000001");
		request.setIdCardFrontImg("idCardFrontImg0000002");
		request.setIdCardNo("331021198309052817");
		List<CertificateImgInfo> certificateImgs=new ArrayList<CertificateImgInfo>();
		CertificateImgInfo img=new CertificateImgInfo();
		img.setFileId("cerfid0000001");
		img.setOrderNum(1);
		certificateImgs.add(img);
		 img=new CertificateImgInfo();
			img.setFileId("cerfid0000002");
			img.setOrderNum(2);
			certificateImgs.add(img);
			 img=new CertificateImgInfo();
				img.setFileId("cerfid0000003");
				img.setOrderNum(3);
				certificateImgs.add(img);
		
		request.setCertificateImgs(certificateImgs);
		AgreeElectricianInvitationResponse resp = this.sendRequest(request,
				1002064, AgreeElectricianInvitationResponse.class);
		log.info("resp:{}", resp);
	}

}
