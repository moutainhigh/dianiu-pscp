package test.edianniu.sps.tcp.workorder;

import com.edianniu.pscp.sps.bean.request.workOrder.ListRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.ListResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: List
 * Author: tandingbo
 * CreateTime: 2017-06-26 15:56
 */
public class List extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(List.class);
    private int msgcode = 1002094;

    @Test
    public void test() throws IOException {
        ListRequest request = new ListRequest();
        request.setUid(1243L);
        request.setToken("79449236");

        request.setOffset(0);
        request.setStatus("ongoing");
  //      request.setLongitude("30.18339");
 //       request.setLatitude("120.1493");
//        request.setName("测试");

        ListResponse resp = super.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
