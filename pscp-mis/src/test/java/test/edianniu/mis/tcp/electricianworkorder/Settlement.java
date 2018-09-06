package test.edianniu.mis.tcp.electricianworkorder;

import com.edianniu.pscp.mis.bean.request.electricianworkorder.SettlementRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.SettlementResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Settlement
 * Author: tandingbo
 * CreateTime: 2017-06-05 16:23
 */
public class Settlement extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(StartWorkOrder.class);
    private int msgcode = 1002056;

    @Test
    public void test() throws IOException {
        SettlementRequest request = new SettlementRequest();
        request.setUid(1072L);
        request.setToken("48971286");
        request.setOrderId("EWD10274801322037067");
        request.setAmount("200.00");

        SettlementResponse resp = super.sendRequest(request, msgcode, SettlementResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
