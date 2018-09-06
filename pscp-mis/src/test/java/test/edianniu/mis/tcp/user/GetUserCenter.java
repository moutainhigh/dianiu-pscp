package test.edianniu.mis.tcp.user;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;

import com.alibaba.fastjson.JSON;
import com.edianniu.pscp.mis.bean.request.user.GetUserRequest;
import com.edianniu.pscp.mis.bean.response.user.GetUserResponse;

public class GetUserCenter extends AbstractLocalTest {
	private static final Logger log = LoggerFactory
			.getLogger(GetUserCenter.class);


	//@Test
	public void companyElelctrician() throws IOException {
		GetUserRequest request = new GetUserRequest();
		request.setUid(1184L);
		request.setToken("83829755");
		GetUserResponse resp = this.sendRequest(request, 1002027,
				GetUserResponse.class);
		log.info("resp:{}", resp);
		log.info("resp json:{}", JSON.toJSONString(resp));
	}

	//@Test
	public void socialElectrician() throws IOException {
        
		GetUserRequest request = new GetUserRequest();
		request.setUid(1191L);
		request.setToken("56255627");
		GetUserResponse resp = this.sendRequest(request, 1002027,
				GetUserResponse.class);
		log.info("resp:{}", resp);
		log.info("resp json:{}", JSON.toJSONString(resp));

	}
	@Test
	public void normalMember()throws IOException {
		GetUserRequest request = new GetUserRequest();
		request.setUid(1194L);
		request.setToken("84724664");
		GetUserResponse resp = this.sendRequest(request, 1002027,GetUserResponse.class);
		log.info("resp:{}", resp);
		log.info("resp json:{}", JSON.toJSONString(resp));
		
	}

	

}
