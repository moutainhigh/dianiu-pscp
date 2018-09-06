package test.edianniu.cs.tcp.operationrecord;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.request.operationrecord.ListRequest;
import com.edianniu.pscp.cs.bean.response.operationrecord.ListResponse;
import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 配电房操作记录列表
 * @author zhoujianjian
 * @date 2017年10月29日 下午11:06:26
 */
public class OperationRecordList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(OperationRecordList.class);
    private int msgcode = 1002149;

    @Test
    public void test() throws IOException {
        ListRequest request = new ListRequest();
        request.setUid(1226L);
        request.setToken("79449236");
        request.setOffset(0);
        
        request.setRoomId(-1L);
       
        
        ListResponse resp = super.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
