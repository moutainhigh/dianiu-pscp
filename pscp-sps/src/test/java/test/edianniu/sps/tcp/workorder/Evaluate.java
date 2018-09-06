package test.edianniu.sps.tcp.workorder;

import com.edianniu.pscp.sps.bean.request.workOrder.EvaluateRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.EvaluateResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Evaluate
 * Author: tandingbo
 * CreateTime: 2017-06-29 10:09
 */
public class Evaluate extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Evaluate.class);
    private int msgcode = 1002099;

    @Test
    public void test() throws IOException {
        EvaluateRequest request = new EvaluateRequest();
        request.setUid(1185L);
        request.setToken("79449236");
        request.setOrderId("GD1638738941364720");

        EvaluateResponse resp = super.sendRequest(request, msgcode, EvaluateResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
