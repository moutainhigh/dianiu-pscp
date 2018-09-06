/**
 *
 */
package test.edianniu.mis.tcp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edianniu.pscp.mis.bean.request.TerminalRequest;
import com.edianniu.pscp.mis.bean.response.BaseResponse;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.until.ByteUtil;
import test.edianniu.mis.tcp.until.HeadUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author elliot.chen
 */
public abstract class AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(AbstractLocalTest.class);

    protected static String ServerIP = "127.0.0.1";

    protected static int Port = 20007; // 未加密

    /*
    Abner 2015年11月17日13:27:23
    增加 junit 测试工具.
    begin
    */
    private static Socket socket;

    private static DataOutputStream dos;

    private static DataInputStream dis;

    private byte[] resp = new byte[2048000];

    @Before
    public void setUp() throws Exception {
        socket = new Socket(ServerIP, Port);
        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());
    }

    @After
    public void tearDown() throws Exception {
        if (dis != null) {
            dis.close();
        }
        if (dis != null) {
            dos.flush();
        }
        if (dis != null) {
            socket.close();
        }
    }

    /**
     * 发送请求, 并获得结果
     *
     * @param request 请求对象 需为TerminalRequest 或其子类
     * @param msgcode message code
     * @return 调用返回的json
     * @throws IOException omit
     */
    protected String sendRequest(TerminalRequest request, int msgcode) throws IOException {
        //request.setUid(1000L);
        //request.setToken("13212743");
        byte[] body = JSONObject.toJSONBytes(request);
        body = HeadUtil.tcpheadJson(body, ByteUtil.int2bytes(msgcode));
        log.info("--------------------------请求信息--------------------------");
        log.info(String.format("request json : %s", JSON.toJSONString(request)));
        log.info("req:" + ByteUtil.dumpBytesAsHEX(body));
        dos.write(body);
        // dos.flush();
        dis.read(resp);
        log.info("--------------------------响应信息--------------------------");
        log.info("resp:" + ByteUtil.dumpBytesAsHEX(resp));
        int lenhth = ByteUtil.bytes2int(resp, 1, 3);
        byte[] bytes = new byte[lenhth - 32];
        for (int i = 0; i < lenhth - 32; i++) {
            bytes[i] = resp[i + 32];
        }

        return new String(bytes);
    }

    /**
     * 发送请求, 并获得结果
     *
     * @param request     请求对象 需为TerminalRequest 或其子类
     * @param msgcode     message code
     * @param resultClass 返回结果的类型,必须为 -code-com.edianniu.mis.bean.response.BaseResponse-/code-的子类
     * @param -T-         返回值类型
     * @return 调用结果
     * @throws IOException omit
     */
    protected <T extends BaseResponse> T sendRequest(TerminalRequest request, int msgcode, Class<T> resultClass) throws IOException {
        String result = this.sendRequest(request, msgcode);
        log.info(String.format("result json : \n\t%s\n", result));
        return JSONObject.parseObject(result, resultClass);
    }
}
