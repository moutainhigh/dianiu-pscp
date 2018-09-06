package test.edianniu.cs.tcp.equipment;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.equipment.DeleteRequest;
import com.edianniu.pscp.cs.bean.response.equipment.DeleteResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 配电房设备删除
 * @author zhoujianjian
 * 2017年9月29日下午11:01:38
 */
public class CustomerEquipmentDelete extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(CustomerEquipmentDelete.class);
    private int msgcode = 1002148;

    @Test
    public void test() throws IOException {
        DeleteRequest request = new DeleteRequest();
        request.setUid(1187L);
        request.setToken("79449236");
        
        request.setId(1002L);
        
        DeleteResponse resp = super.sendRequest(request, msgcode, DeleteResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
