package test.edianniu.cs.tcp.needs;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.request.needs.AuditRequest;
import com.edianniu.pscp.cs.bean.response.needs.AuditResponse;
import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 需求审核
 * @author zhoujianjian
 * 2017年9月22日下午3:46:19
 */
public class NeedsAudit extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(NeedsAudit.class);
    private int msgcode = 1002156;

    @Test
    public void test() throws IOException {
        AuditRequest request = new AuditRequest();
        request.setUid(1187L);
        request.setToken("79449236");
        
        request.setOrderId("NE15616201001936");
        request.setStatus(1);
        request.setMaskString("点点滴滴");
        //request.setRemovedImgs("1009");
        
        AuditResponse resp = super.sendRequest(request, msgcode, AuditResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
