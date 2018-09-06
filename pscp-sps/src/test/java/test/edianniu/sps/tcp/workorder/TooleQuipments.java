package test.edianniu.sps.tcp.workorder;

import com.edianniu.pscp.sps.bean.request.workOrder.TooleQuipmentsRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.TooleQuipmentsResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: TooleQuipments
 * Author: tandingbo
 * CreateTime: 2017-08-04 09:59
 */
public class TooleQuipments extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(TooleQuipments.class);
    private int msgcode = 1002113;

    @Test
    public void test() throws IOException {
        TooleQuipmentsRequest request = new TooleQuipmentsRequest();
        request.setUid(1185L);
        request.setToken("79449236");

        TooleQuipmentsResponse resp = super.sendRequest(request, msgcode, TooleQuipmentsResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
