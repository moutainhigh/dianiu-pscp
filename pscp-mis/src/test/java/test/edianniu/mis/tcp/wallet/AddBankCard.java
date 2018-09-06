package test.edianniu.mis.tcp.wallet;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.request.wallet.AddBankCardRequest;
import com.edianniu.pscp.mis.bean.response.wallet.AddBankCardResponse;


public class AddBankCard extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(AddBankCard.class);

	private static Socket socket;

	private static DataOutputStream dos;

	private static DataInputStream dis;

	byte[] resp = new byte[10240];

	private byte[] msgcode = ByteUtil.int2bytes(1002036);

	@Before
	public void setUp() throws Exception {
		socket = new Socket(ServerIP, Port);
		dos = new DataOutputStream(socket.getOutputStream());
		dis = new DataInputStream(socket.getInputStream());
	}

	@Test
	public void test() throws IOException {

		AddBankCardRequest request = new AddBankCardRequest();
		request.setUid(1226L);
		request.setToken("63767192");
		request.setBankBranchName("中国农业银行");
		request.setBankId(1002L);
		request.setAccount("6228480322625138514");
		request.setProvinceId(7L);
		request.setCityId(56L);
		//request.setMsgcode("34439");
		//request.setMsgcodeid("ac665c06-3a98-429a-97ec-550e2501b31e");
		
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
		AddBankCardResponse resp = JSONObject.parseObject(result, AddBankCardResponse.class);
		System.out.println(resp.getResultMessage());
		System.out.println(resp.getResultCode());
		System.out.println(JSON.toJSONString(resp));

	}

	@After
	public void tearDown() throws Exception {
		dis.close();
		dos.flush();
		socket.close();
	}

}
