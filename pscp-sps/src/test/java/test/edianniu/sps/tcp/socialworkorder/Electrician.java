package test.edianniu.sps.tcp.socialworkorder;

import com.edianniu.pscp.sps.bean.request.socialworkorder.ElectricianRequest;
import com.edianniu.pscp.sps.bean.response.socialworkorder.ElectricianResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Electrician
 * Author: tandingbo
 * CreateTime: 2017-06-29 16:10
 */
public class Electrician extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Electrician.class);
    private int msgcode = 1002104;

    @Test
    public void test() throws IOException {
        ElectricianRequest request = new ElectricianRequest();
        request.setUid(1185L);
        request.setToken("79449236");

        request.setOrderId("SGD35858020407920");

        ElectricianResponse resp = super.sendRequest(request, msgcode, ElectricianResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
