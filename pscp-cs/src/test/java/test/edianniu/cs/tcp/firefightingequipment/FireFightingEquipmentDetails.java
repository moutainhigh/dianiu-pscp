package test.edianniu.cs.tcp.firefightingequipment;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.firefightingequipment.DetailsRequest;
import com.edianniu.pscp.cs.bean.response.firefightingequipment.DetailsResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 配电房安全用具详情
 * @author zhoujianjian
 * @date 2017年11月1日 上午9:46:21
 */
public class FireFightingEquipmentDetails extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(FireFightingEquipmentDetails.class);
    private int msgcode = 1002137;

    @Test
    public void test() throws IOException {
        DetailsRequest request = new DetailsRequest();
        request.setUid(1221L);
        request.setToken("79449236");
       
        request.setId(1007L);
        
        DetailsResponse resp = super.sendRequest(request, msgcode, DetailsResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
