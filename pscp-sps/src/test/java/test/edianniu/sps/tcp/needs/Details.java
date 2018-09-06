package test.edianniu.sps.tcp.needs;

import com.edianniu.pscp.sps.bean.request.needs.DetailsRequest;
import com.edianniu.pscp.sps.bean.response.needs.DetailsResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Details
 * Author: tandingbo
 * CreateTime: 2017-09-25 16:56
 */
public class Details extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(test.edianniu.sps.tcp.socialworkorder.Details.class);
    private int msgcode = 1002169;

    @Test
    public void test() throws IOException {
        DetailsRequest request = new DetailsRequest();
        request.setUid(1250L);
        request.setToken("41439513");

//        request.setOrderId("NE6389697648850720");
        request.setResponsedOrderId("NEO115624019379096");

        DetailsResponse resp = super.sendRequest(request, msgcode, DetailsResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
