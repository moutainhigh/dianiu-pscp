package test.edianniu.cs.tcp.dutylog;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.dutylog.DeleteRequest;
import com.edianniu.pscp.cs.bean.response.dutylog.DeleteResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 配电房值班日志删除
 * @author zhoujianjian
 * @date 2017年10月30日 下午7:51:52
 */
public class DutyLogDelete extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(DutyLogDelete.class);
    private int msgcode = 1002143;

    @Test
    public void test() throws IOException {
        DeleteRequest request = new DeleteRequest();
        request.setUid(1187L);
        request.setToken("79449236");
        
        request.setId(1001L);
        
        DeleteResponse resp = super.sendRequest(request, msgcode, DeleteResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
