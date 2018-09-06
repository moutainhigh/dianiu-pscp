package test.edianniu.sps.tcp.socialworkorder;

import com.edianniu.pscp.sps.bean.request.socialworkorder.DetailsRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.DetailsResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Details
 * Author: tandingbo
 * CreateTime: 2017-06-29 13:54
 */
public class Details extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Details.class);
    private int msgcode = 1002102;

    @Test
    public void test() throws IOException {
        DetailsRequest request = new DetailsRequest();
        request.setUid(1185L);
        request.setToken("41439513");

        request.setOrderId("SGD430018938174720");

        DetailsResponse resp = super.sendRequest(request, msgcode, DetailsResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
