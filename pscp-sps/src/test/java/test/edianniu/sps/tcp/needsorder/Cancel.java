package test.edianniu.sps.tcp.needsorder;

import com.edianniu.pscp.sps.bean.request.needsorder.CancelRequest;
import com.edianniu.pscp.sps.bean.response.needsorder.CancelResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Cancel
 * Author: tandingbo
 * CreateTime: 2017-09-26 11:35
 */
public class Cancel extends AbstractLocalTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private int msgcode = 1002175;

    @Test
    public void test() throws IOException {
        CancelRequest request = new CancelRequest();
        request.setUid(1185L);
        request.setToken("32581897");

        request.setOrderId("NEO46519876629299");

        CancelResponse resp = super.sendRequest(request, msgcode, CancelResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
