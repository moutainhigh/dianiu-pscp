package test.edianniu.cs.tcp.needs;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.request.needs.ConfirmCooperationRequest;
import com.edianniu.pscp.cs.bean.response.needs.ConfirmCooperationResponse;
import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 
 * @author zhoujianjian
 * 2017年9月12日下午4:17:04
 */
public class NeedsConfirmCooperation extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(NeedsConfirmCooperation.class);
    private int msgcode = 1002128;

    @Test
    public void test() throws IOException {
        ConfirmCooperationRequest request = new ConfirmCooperationRequest();
        request.setUid(1226L);
        request.setToken("79449236");
        
        request.setOrderId("NE1039201845064598");
        request.setResponsedOrderId("NEO1142693084372737");
        
        
        ConfirmCooperationResponse resp = super.sendRequest(request, msgcode, ConfirmCooperationResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
