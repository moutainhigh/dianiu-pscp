package test.edianniu.cs.tcp.equipment;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.equipment.DetailsRequest;
import com.edianniu.pscp.cs.bean.response.equipment.DetailsResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 配电房设备详情
 * @author zhoujianjian
 * 2017年9月29日下午10:23:30
 */
public class CustomerEquipmentDetails extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(CustomerEquipmentDetails.class);
    private int msgcode = 1002147;

    @Test
    public void test() throws IOException {
        DetailsRequest request = new DetailsRequest();
        request.setUid(1187L);
        request.setToken("79449236");
        request.setId(1006L);
        
        DetailsResponse resp = super.sendRequest(request, msgcode, DetailsResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
