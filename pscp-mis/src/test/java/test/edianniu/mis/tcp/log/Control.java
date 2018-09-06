package test.edianniu.mis.tcp.log;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.edianniu.pscp.mis.bean.request.log.NetDauControlRequest;
import com.edianniu.pscp.mis.bean.response.log.NetDauControlResponse;

public class Control extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(Control.class);

	

	@Test
	public void control() throws IOException {

		NetDauControlRequest request = new NetDauControlRequest();
		
		request.setUid(1061L);
		request.setToken("72948133");
		NetDauControlResponse response=this.sendRequest(request, 1003006, NetDauControlResponse.class);
		log.info("resp:{}",response);

	}

	
}
