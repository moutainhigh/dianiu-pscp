package test.edianniu.mis.tcp;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.mis.bean.request.GetProvincesRequest;
import com.edianniu.pscp.mis.bean.response.GetProvincesResponse;


public class GetProvinces extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(GetProvinces.class);

	@Test
	public void test() throws IOException {

		GetProvincesRequest request = new GetProvincesRequest();
		request.setUid(1207L);
		request.setToken("74961151");
		GetProvincesResponse response=this.sendRequest(request, 1002062, GetProvincesResponse.class);
		log.info("resp:",response);

		

	}

}
