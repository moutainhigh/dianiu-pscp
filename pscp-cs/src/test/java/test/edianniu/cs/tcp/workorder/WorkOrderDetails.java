package test.edianniu.cs.tcp.workorder;

import com.edianniu.pscp.cs.bean.request.workorder.DetailsRequest;
import com.edianniu.pscp.cs.bean.response.workorder.DetailsResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.cs.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: WorkOrderDetails
 * Author: tandingbo
 * CreateTime: 2017-08-14 11:56
 */
public class WorkOrderDetails extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(WorkOrderDetails.class);
    private int msgcode = 1002116;

    @Test
    public void test() throws IOException {
        DetailsRequest request = new DetailsRequest();
        request.setUid(1288L);
        request.setToken("79449236");

        request.setOrderId("GD178434207318353");

        DetailsResponse resp = super.sendRequest(request, msgcode, DetailsResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
