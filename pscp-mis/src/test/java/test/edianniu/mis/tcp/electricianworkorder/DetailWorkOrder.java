package test.edianniu.mis.tcp.electricianworkorder;

import com.edianniu.pscp.mis.bean.request.electricianworkorder.DetailRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.DetailResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: DetailWorkOrder
 * Author: tandingbo
 * CreateTime: 2017-04-26 10:43
 */
public class DetailWorkOrder extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(DetailWorkOrder.class);
    private int msgcode = 1002018;

    @Test
    public void test() throws IOException {
        DetailRequest request = new DetailRequest();
        request.setUid(1187L);
        request.setToken("31589328");
        request.setOrderId("EGD1298498325812318");

        DetailResponse resp = super.sendRequest(request, msgcode, DetailResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
