package test.edianniu.sps.tcp.workorder;

import com.edianniu.pscp.sps.bean.request.workOrder.ElectricianRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.ElectricianResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Electrician
 * Author: tandingbo
 * CreateTime: 2017-06-29 10:45
 */
public class Electrician extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Evaluate.class);
    private int msgcode = 1002100;

    @Test
    public void test() throws IOException {
        ElectricianRequest request = new ElectricianRequest();
        request.setUid(1060L);
        request.setToken("79449236");

        ElectricianResponse resp = super.sendRequest(request, msgcode, ElectricianResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
