package test.edianniu.mis.tcp.socialworkorder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;
import test.edianniu.mis.tcp.until.ByteUtil;
import test.edianniu.mis.tcp.until.HeadUtil;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.request.socialworkorder.OnOffRequest;
import com.edianniu.pscp.mis.bean.response.socialworkorder.OnOffResponse;

public class OnOff extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(OnOff.class);


	@Before
	

	@Test
	public void onoff() throws IOException {

		OnOffRequest request = new OnOffRequest();
		request.setUid(1034L);
		request.setToken("65788394");
		request.setStatus("on");//on off;
		OnOffResponse resp=this.sendRequest(request, 1002044, OnOffResponse.class);
		log.info("resp:{}",resp);
	}

	

}
