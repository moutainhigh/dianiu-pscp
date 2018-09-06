package test.edianniu.mis.tcp.user;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.request.user.FeedbackRequest;
import com.edianniu.pscp.mis.bean.response.user.FeedbackResponse;

public class FeedBack extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(FeedBack.class);
	@Test
	public void feedback() throws IOException {

		FeedbackRequest request = new FeedbackRequest();
		request.setUid(1184L);
		request.setToken("17942947");
		request.setContent("xxxxxxx");
		request.setAppPkg("com.edianniu.pscp.electrician");
		request.setClientHost("127.0.0.2");
		request.setClientId("xxxxxxxxxxxxx");
		request.setDeviceToken("xxxxxxxxxxxxxxxx2");
		request.setDid("sfsdfsdfsdf");
		request.setNetworkType("xxxxxx2123123");
		request.setOsType("android");
		request.setOsVersion("1.009");
		request.setPbrand("华为");
		request.setPtype("p9");
		request.setRamSize("256M");
		request.setRomSize("128M");
		request.setScreenHeight("600");
		request.setScreenWidth("800");
		request.setShowVersion("v1.001");
		request.setVersion("2017071414");
        FeedbackResponse resp=this.sendRequest(request, 1002048, FeedbackResponse.class);
        log.info("resp:{}",resp);
	}

	
}
