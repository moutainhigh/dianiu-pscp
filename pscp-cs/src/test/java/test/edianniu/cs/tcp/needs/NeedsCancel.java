package test.edianniu.cs.tcp.needs;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.needs.CancelRequest;
import com.edianniu.pscp.cs.bean.response.needs.CancelResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 取消需求
 * @author zhoujianjian
 * 2017年9月14日下午11:40:33
 */
public class NeedsCancel extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(NeedsCancel.class);
    private int msgcode = 1002126;

    @Test
    public void test() throws IOException {
        CancelRequest request = new CancelRequest();
        request.setUid(1226L);
        request.setToken("79449236");
        
        request.setOrderId("NE15616201001936");
        
        CancelResponse resp = super.sendRequest(request, msgcode, CancelResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
