package test.edianniu.mis.tcp.electricianworkorder;

import com.edianniu.pscp.mis.bean.request.electricianworkorder.ListWorkLogRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.ListWorkLogResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: ListWorkLog
 * Author: tandingbo
 * CreateTime: 2017-04-26 11:30
 */
public class ListWorkLog extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(ListWorkLog.class);
    private int msgcode = 1002026;

    @Test
    public void test() throws IOException {
        ListWorkLogRequest request = new ListWorkLogRequest();
        request.setUid(1039L);
        request.setToken("57388449");
        request.setOrderId("OID-12121");

        ListWorkLogResponse resp = super.sendRequest(request, msgcode, ListWorkLogResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
