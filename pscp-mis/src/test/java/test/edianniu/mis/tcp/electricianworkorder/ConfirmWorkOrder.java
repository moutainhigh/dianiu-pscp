package test.edianniu.mis.tcp.electricianworkorder;

import com.edianniu.pscp.mis.bean.request.electricianworkorder.ConfirmRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.ConfirmResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: ConfirmWorkOrder
 * Author: tandingbo
 * CreateTime: 2017-04-26 09:33
 */
public class ConfirmWorkOrder extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(ConfirmWorkOrder.class);
    private int msgcode = 1002019;

    @Test
    public void test() throws IOException {
        ConfirmRequest request = new ConfirmRequest();
        request.setUid(1060L);
        request.setToken("37785743");
        request.setOrderId("EGD45567029086969");

        ConfirmResponse resp = super.sendRequest(request, msgcode, ConfirmResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
