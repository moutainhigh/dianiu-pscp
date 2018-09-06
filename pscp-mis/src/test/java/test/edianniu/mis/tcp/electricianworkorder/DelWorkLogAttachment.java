package test.edianniu.mis.tcp.electricianworkorder;

import com.edianniu.pscp.mis.bean.request.electricianworkorder.DelWorkLogAttachmentRequest;
import com.edianniu.pscp.mis.bean.response.electricianworkorder.DelWorkLogAttachmentResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: DelWorkLogAttachment
 * Author: tandingbo
 * CreateTime: 2017-04-26 13:58
 */
public class DelWorkLogAttachment extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(DelWorkLogAttachment.class);
    private int msgcode = 1002025;

    @Test
    public void test() throws IOException {
        DelWorkLogAttachmentRequest request = new DelWorkLogAttachmentRequest();
        request.setUid(1039L);
        request.setToken("57388449");
        request.setOrderId("OID-12121");
        request.setWorkLogAttachmentId(1001L);

        DelWorkLogAttachmentResponse resp = super.sendRequest(request, msgcode, DelWorkLogAttachmentResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
