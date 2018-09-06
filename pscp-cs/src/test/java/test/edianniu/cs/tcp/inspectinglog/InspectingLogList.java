package test.edianniu.cs.tcp.inspectinglog;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.inspectinglog.ListRequest;
import com.edianniu.pscp.cs.bean.response.inspectinglog.ListResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 消防设施列表
 * @author zhoujianjian
 * @date 2017年11月1日 下午7:25:02
 */
public class InspectingLogList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(InspectingLogList.class);
    private int msgcode = 1002136;

    @Test
    public void test() throws IOException {
        ListRequest request = new ListRequest();
        request.setUid(1204L);
        request.setToken("79449236");
        request.setOffset(10);
        
        request.setEquipmentId(1004L);
        request.setType(2);
        
        ListResponse resp = super.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
