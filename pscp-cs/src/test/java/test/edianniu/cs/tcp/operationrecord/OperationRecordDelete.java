package test.edianniu.cs.tcp.operationrecord;


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
public class OperationRecordDelete extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(OperationRecordDelete.class);
    private int msgcode = 1002152;

    @Test
    public void test() throws IOException {
        DeleteRequest request = new DeleteRequest();
        request.setUid(1187L);
        request.setToken("79449236");
        
        request.setId(1006L);
        
        DeleteResponse resp = super.sendRequest(request, msgcode, DeleteResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
