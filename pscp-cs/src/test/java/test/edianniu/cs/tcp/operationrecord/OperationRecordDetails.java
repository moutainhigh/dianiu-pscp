package test.edianniu.cs.tcp.operationrecord;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.equipment.DetailsRequest;
import com.edianniu.pscp.cs.bean.response.equipment.DetailsResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 配电房操作记录详情
 * @author zhoujianjian
 * @date 2017年10月30日 上午11:03:09
 */
public class OperationRecordDetails extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(OperationRecordDetails.class);
    private int msgcode = 1002150;

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
