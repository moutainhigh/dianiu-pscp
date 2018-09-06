package test.edianniu.sps.tcp.needs;

import com.edianniu.pscp.sps.bean.request.needs.ListRequest;
import com.edianniu.pscp.sps.bean.response.needs.ListResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: List
 * Author: tandingbo
 * CreateTime: 2017-09-25 16:55
 */
public class List extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(test.edianniu.sps.tcp.socialworkorder.List.class);
    private int msgcode = 1002168;

    @Test
    public void test() throws IOException {
        ListRequest request = new ListRequest();
        request.setUid(1185L);
        request.setToken("32581897");

        request.setOffset(0);

        ListResponse resp = super.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
