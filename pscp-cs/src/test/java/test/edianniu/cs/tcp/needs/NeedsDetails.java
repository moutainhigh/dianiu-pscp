package test.edianniu.cs.tcp.needs;


import com.edianniu.pscp.cs.bean.request.needs.DetailsRequest;
import com.edianniu.pscp.cs.bean.response.needs.DetailsResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.cs.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * 需求详情
 * @author zhoujianjian
 * 2017年9月18日上午11:44:45
 */
public class NeedsDetails extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(NeedsDetails.class);
    private int msgcode = 1002125;

    @Test
    public void test() throws IOException {
        DetailsRequest request = new DetailsRequest();
        request.setUid(1226L);
        request.setToken("18637323");
        
        request.setOrderId("NE2094793774294256");
        
        
        DetailsResponse resp = super.sendRequest(request, msgcode, DetailsResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
