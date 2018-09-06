package test.edianniu.mis.tcp.electricianworkorder;

import com.edianniu.pscp.mis.bean.request.electricianworkorder.StartWorkRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.StartWorkResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: StartWorkOrder
 * Author: tandingbo
 * CreateTime: 2017-04-26 09:36
 */
public class StartWorkOrder extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(StartWorkOrder.class);
    private int msgcode = 1002021;

    @Test
    public void test() throws IOException {
        StartWorkRequest request = new StartWorkRequest();
        request.setUid(1356L);
        request.setToken("37785743");
        request.setOrderId("EGD178487802388677");
        request.setStartTime("2018-01-22 20:01:00");

        StartWorkResponse resp = super.sendRequest(request, msgcode, StartWorkResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
