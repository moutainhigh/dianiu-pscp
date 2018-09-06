package test.edianniu.sps.tcp.needsorder;

import com.edianniu.pscp.sps.bean.request.needsorder.ListRequest;
import com.edianniu.pscp.sps.bean.response.needsorder.ListResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: List
 * Author: tandingbo
 * CreateTime: 2017-09-26 10:42
 */
public class List extends AbstractLocalTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private int msgcode = 1002170;

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
