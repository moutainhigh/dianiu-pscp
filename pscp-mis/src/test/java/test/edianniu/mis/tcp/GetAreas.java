package test.edianniu.mis.tcp;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.mis.bean.request.GetAreasRequest;
import com.edianniu.pscp.mis.bean.response.GetAreasResponse;


public class GetAreas extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(GetAreas.class);

	@Test
	public void test() throws IOException {

		GetAreasRequest request = new GetAreasRequest();
		request.setUid(1207L);
		request.setToken("74961151");
		request.setCityId(1L);
		GetAreasResponse response=this.sendRequest(request, 1002058, GetAreasResponse.class);
		log.info("resp:",response);

		

	}

}
