package test.edianniu.mis.tcp.user;

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
import com.edianniu.pscp.mis.bean.request.user.ResetPwdByMsgCodeRequest;
import com.edianniu.pscp.mis.bean.response.user.ResetPwdByMsgCodeResponse;

public class ResetPwdByMsgCode extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(ResetPwdByMsgCode.class);

	private static Socket socket;

	private static DataOutputStream dos;

	private static DataInputStream dis;

	byte[] resp = new byte[10240];

	private byte[] msgcode = ByteUtil.int2bytes(1001006);

	@Before
	public void setUp() throws Exception {
		socket = new Socket(ServerIP, Port);
		dos = new DataOutputStream(socket.getOutputStream());
		dis = new DataInputStream(socket.getInputStream());
	}

	@Test
	public void register() throws IOException {

		ResetPwdByMsgCodeRequest request = new ResetPwdByMsgCodeRequest();
		request.setUid(1034L);
		request.setToken("19653372");
		request.setMsgcode("00091");
		request.setMsgcodeid("9a4bfd09-b51e-4398-90fb-4c8b9c796133");
		request.setPasswd("654321");
		request.setMobile("18358141929");

		byte[] body = JSONObject.toJSONBytes(request);
		body = HeadUtil.tcpheadJson(body, msgcode);
		log.info("---------------请求信息------------");
		log.info("req:" + ByteUtil.dumpBytesAsHEX(body));
		dos.write(body);
		// dos.flush();
		dis.read(resp);
		log.info("--------------------------响应信息----------------------------------");
		log.info("resp:" + ByteUtil.dumpBytesAsHEX(resp));
		int lenhth = ByteUtil.bytes2int(resp, 1, 3);
		byte[] bytes = new byte[lenhth - 32];
		for (int i = 0; i < lenhth - 32; i++) {
			bytes[i] = resp[i + 32];
		}
		String result = new String(bytes);
		ResetPwdByMsgCodeResponse resp = JSONObject
				.parseObject(result, ResetPwdByMsgCodeResponse.class);
		System.out.println(resp.getResultMessage());
		System.out.println(resp.getResultCode());

	}

	@After
	public void tearDown() throws Exception {
		dis.close();
		dos.flush();
		socket.close();
	}

}
