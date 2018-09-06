package test.edianniu.cs.tcp.dutylog;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.dutylog.ListRequest;
import com.edianniu.pscp.cs.bean.response.dutylog.ListResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 值班日志
 * @author zhoujianjian
 * @date 2017年10月30日 下午5:01:26
 */
public class DutyLogList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(DutyLogList.class);
    private int msgcode = 1002144;

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
