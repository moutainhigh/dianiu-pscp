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
import com.edianniu.pscp.mis.bean.request.wallet.DaybookRequest;
import com.edianniu.pscp.mis.bean.request.wallet.WalletHomeRequest;
import com.edianniu.pscp.mis.bean.response.wallet.DaybookResponse;
import com.edianniu.pscp.mis.bean.response.wallet.WalletHomeResponse;


public class Daybook extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(Daybook.class);

	private static Socket socket;

	private static DataOutputStream dos;

	private static DataInputStream dis;

	byte[] resp = new byte[10240];

	private byte[] msgcode = ByteUtil.int2bytes(1002029);

	@Before
	public void setUp() throws Exception {
		socket = new Socket(ServerIP, Port);
		dos = new DataOutputStream(socket.getOutputStream());
		dis = new DataInputStream(socket.getInputStream());
	}

	@Test
	public void test() throws IOException {

		DaybookRequest request = new DaybookRequest();
		request.setUid(1060L);
		request.setToken("69492939");
		request.setOffset(10);
		request.setLimit(20);

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
		DaybookResponse resp = JSONObject.parseObject(result, DaybookResponse.class);
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
