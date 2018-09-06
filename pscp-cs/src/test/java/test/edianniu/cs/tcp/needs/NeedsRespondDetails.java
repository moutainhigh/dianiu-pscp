package test.edianniu.cs.tcp.needs;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.request.needs.RespondDetailsRequest;
import com.edianniu.pscp.cs.bean.response.needs.RespondDetailsResponse;
import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 服务商资质
 * @author zhoujianjian
 * 2017年9月12日下午4:17:04
 */
public class NeedsRespondDetails extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(NeedsRespondDetails.class);
    private int msgcode = 1002130;

    @Test
    public void test() throws IOException {
        RespondDetailsRequest request = new RespondDetailsRequest();
        request.setUid(1225L);
        request.setToken("79449236");
        
        request.setOrderId("NEO1142693084372737");
       
        RespondDetailsResponse resp = super.sendRequest(request, msgcode, RespondDetailsResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
