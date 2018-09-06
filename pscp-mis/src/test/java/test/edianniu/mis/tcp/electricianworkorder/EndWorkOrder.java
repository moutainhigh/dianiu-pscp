package test.edianniu.mis.tcp.electricianworkorder;

import com.edianniu.pscp.mis.bean.request.electricianworkorder.EndWorkRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.EndWorkResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: EndWorkOrder
 * Author: tandingbo
 * CreateTime: 2017-04-26 11:12
 */
public class EndWorkOrder extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(EndWorkOrder.class);
    private int msgcode = 1002022;

    @Test
    public void test() throws IOException {
        EndWorkRequest request = new EndWorkRequest();
        request.setUid(1356L);
        request.setToken("35115726");
        request.setOrderId("EGD178487802388677");
        request.setEndTime("2018-01-22 20:11:00");

        EndWorkResponse resp = super.sendRequest(request, msgcode, EndWorkResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
