package test.edianniu.cs.tcp.power;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.request.power.PowerLoadRequest;
import com.edianniu.pscp.cs.bean.response.power.PowerLoadResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 客户线路列表
 * @author zhoujianjian
 * @date 2017年12月7日 下午4:28:13
 */
public class PowerLoad extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(PowerLoad.class);
    private int msgcode = 1002182;

    @Test
    public void test() throws IOException {
        PowerLoadRequest request = new PowerLoadRequest();
        request.setUid(1243L);
        request.setToken("79449236");

        PowerLoadResponse resp = super.sendRequest(request, msgcode, PowerLoadResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
