package test.edianniu.mis.tcp;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.mis.bean.request.CheckAppUpdateRequest;
import com.edianniu.pscp.mis.bean.response.CheckAppUpdateResponse;

public class AppCheckUpdate extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(AppCheckUpdate.class);

	


	@Test
	public void test() throws IOException {

		CheckAppUpdateRequest request = new CheckAppUpdateRequest();
		request.setUid(1207L);
		request.setToken("74961151");
		request.setAppPkg("com.edianniu.pscp.electrician");
		request.setAppVer(22L);
		request.setOsType("android");
		request.setOsVersion("1711271107");
		CheckAppUpdateResponse resp=this.sendRequest(request, 1002049, CheckAppUpdateResponse.class);
        log.debug("resp:{}",resp);
		

	}


}
