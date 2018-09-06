package test.edianniu.cs.tcp.safetyequipment;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.safetyequipment.DetailsRequest;
import com.edianniu.pscp.cs.bean.response.safetyequipment.DetailsResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 配电房安全用具详情
 * @author zhoujianjian
 * @date 2017年11月1日 上午9:46:21
 */
public class SafetyEquipmentDetails extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(SafetyEquipmentDetails.class);
    private int msgcode = 1002133;

    @Test
    public void test() throws IOException {
        DetailsRequest request = new DetailsRequest();
        request.setUid(1187L);
        request.setToken("79449236");
       
        request.setId(1000L);
        
        DetailsResponse resp = super.sendRequest(request, msgcode, DetailsResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
