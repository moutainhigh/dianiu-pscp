package test.edianniu.sps.tcp.electricianworkorder;

import com.edianniu.pscp.sps.bean.request.electricianworkorder.DetailsRequest;
import com.edianniu.pscp.sps.bean.response.electricianworkorder.DetailsResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Details
 * Author: tandingbo
 * CreateTime: 2017-07-11 15:19
 */
public class Details extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(List.class);
    private int msgcode = 1002111;

    @Test
    public void test() throws IOException {
        DetailsRequest request = new DetailsRequest();
        request.setUid(1185L);
        request.setToken("79449236");

        request.setOrderId("EGD863237331872720");

        DetailsResponse resp = super.sendRequest(request, msgcode, DetailsResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
