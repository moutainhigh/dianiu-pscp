package test.edianniu.cs.tcp.workorder;

import com.edianniu.pscp.cs.bean.request.workorder.ListRequest;
import com.edianniu.pscp.cs.bean.response.workorder.ListResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.cs.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: WorkOrderList
 * Author: tandingbo
 * CreateTime: 2017-08-11 14:44
 */
public class WorkOrderList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(WorkOrderList.class);
    private int msgcode = 1002115;

    @Test
    public void test() throws IOException {
        ListRequest request = new ListRequest();
        request.setUid(1201L);
        request.setToken("79449236");

        request.setOffset(20);
        request.setStatus("underway");
//        request.setName("测试");

        ListResponse resp = super.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
