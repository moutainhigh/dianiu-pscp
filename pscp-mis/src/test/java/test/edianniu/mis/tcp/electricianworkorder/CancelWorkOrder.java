package test.edianniu.mis.tcp.electricianworkorder;

import com.edianniu.pscp.mis.bean.request.electricianworkorder.CancelRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.CancelResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: CancelWorkOrder
 * Author: tandingbo
 * CreateTime: 2017-04-25 17:03
 */
public class CancelWorkOrder extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(CancelWorkOrder.class);
    private int msgcode = 1002020;

    @Test
    public void test() throws IOException {
        CancelRequest request = new CancelRequest();
        request.setUid(1143L);
        request.setToken("33572771");
        request.setOrderId("EWD499996679749061");

        CancelResponse resp = super.sendRequest(request, msgcode, CancelResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
