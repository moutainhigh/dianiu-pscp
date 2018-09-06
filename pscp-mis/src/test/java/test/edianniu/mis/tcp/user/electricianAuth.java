package test.edianniu.mis.tcp.user;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.edianniu.mis.tcp.AbstractLocalTest;
import test.edianniu.mis.tcp.until.ByteUtil;
import test.edianniu.mis.tcp.until.HeadUtil;

import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.electrician.CertificateImgInfo;
import com.edianniu.pscp.mis.bean.request.user.ElectricianAuthRequest;
import com.edianniu.pscp.mis.bean.request.user.GetElectricianRequest;
import com.edianniu.pscp.mis.bean.response.user.GetElectricianResponse;

public class electricianAuth extends AbstractLocalTest {
	private static final Logger log = LoggerFactory.getLogger(electricianAuth.class);

	private static Socket socket;

	private static DataOutputStream dos;

	private static DataInputStream dis;

	byte[] resp = new byte[10240];

	private byte[] msgcode = ByteUtil.int2bytes(1002011);

	@Before
	public void setUp() throws Exception {
		socket = new Socket(ServerIP, Port);
		dos = new DataOutputStream(socket.getOutputStream());
		dis = new DataInputStream(socket.getInputStream());
	}

	@Test
	public void test() throws IOException {

		ElectricianAuthRequest request = new ElectricianAuthRequest();
		request.setUid(1054L);
		request.setToken("53677621");
		List<CertificateImgInfo> certificateImgs=new ArrayList<CertificateImgInfo>();
		CertificateImgInfo info=new CertificateImgInfo();
		info.setFileId("img1/M01/00/00/wKgB-1lA_56APuHLAANS5YG3LqA15..jpg");
		info.setOrderNum(1);
		certificateImgs.add(info);
		request.setCertificateImgs(certificateImgs);
		request.setIdCardBackImg("img1/M00/00/00/wKgB-1lDkz6AKwIKAAc0LY-gL-A31..jpg");
		request.setIdCardFrontImg("img1/M01/00/00/wKgB-1lA_0-AaTHOAAN59cZMxfg97..jpg");
		
		request.setIdCardNo("944844568888643334");
		request.setUserName("曼丽");
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
		System.out.println(result);
		GetElectricianResponse resp = JSONObject
				.parseObject(result, GetElectricianResponse.class);
		System.out.println(resp.getResultCode());
		System.out.println(resp.getResultMessage());
		

	}

	@After
	public void tearDown() throws Exception {
		dis.close();
		dos.flush();
		socket.close();
	}

}
