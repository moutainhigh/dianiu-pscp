package test.edianniu.mis.tcp.socialworkorder;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.request.socialworkorder.ListRequest;
import com.edianniu.pscp.mis.bean.response.socialworkorder.ListResponse;

public class List extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(List.class);

	@Test
	public void register() throws IOException {

		ListRequest request = new ListRequest();
		request.setUid(1095L);
		request.setToken("26227159");
		request.setLatitude("30.193325");
		request.setLongitude("120.433333");
		request.setView("map");
		request.setOffset(0);
		ListResponse response=this.sendRequest(request, 1002014, ListResponse.class);
		log.info("resp:{}",response);

		

	}



}
