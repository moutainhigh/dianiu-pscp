package test.edianniu.mis.tcp.electricianworkorder;

import com.edianniu.pscp.mis.bean.request.electricianworkorder.DelWorkLogRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.DelWorkLogResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: DelWorkLog
 * Author: tandingbo
 * CreateTime: 2017-04-26 12:16
 */
public class DelWorkLog extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(DelWorkLog.class);
    private int msgcode = 1002024;

    @Test
    public void test() throws IOException {
        DelWorkLogRequest request = new DelWorkLogRequest();
        request.setUid(1039L);
        request.setToken("57388449");
        request.setOrderId("OID-12121");
        request.setWorkLogId(1004L);

        DelWorkLogResponse resp = super.sendRequest(request, msgcode, DelWorkLogResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }

}
