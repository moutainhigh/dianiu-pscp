package test.edianniu.sps.tcp.workorder;

import com.edianniu.pscp.sps.bean.request.workOrder.WorkLogRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.WorkLogResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: WorkLog
 * Author: tandingbo
 * CreateTime: 2017-06-29 09:30
 */
public class WorkLog extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(WorkLog.class);
    private int msgcode = 1002098;

    @Test
    public void test() throws IOException {
        WorkLogRequest request = new WorkLogRequest();
        request.setUid(1185L);
        request.setToken("79449236");
        request.setOrderId("GD3908179298546665");

        WorkLogResponse resp = super.sendRequest(request, msgcode, WorkLogResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
