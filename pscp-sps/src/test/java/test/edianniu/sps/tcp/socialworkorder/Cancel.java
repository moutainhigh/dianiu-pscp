package test.edianniu.sps.tcp.socialworkorder;

import com.edianniu.pscp.sps.bean.request.socialworkorder.CancelRequest;
import com.edianniu.pscp.sps.bean.response.socialworkorder.CancelResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Cancel
 * Author: tandingbo
 * CreateTime: 2017-07-11 10:50
 */
public class Cancel extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Cancel.class);
    private int msgcode = 1002108;

    @Test
    public void test() throws IOException {
        CancelRequest request = new CancelRequest();
        request.setUid(1185L);
        request.setToken("79449236");

        request.setOrderId("SGD35858020407920");

        CancelResponse resp = super.sendRequest(request, msgcode, CancelResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }

}
