package test.edianniu.sps.tcp.customer;

import com.edianniu.pscp.sps.bean.request.customer.ListRequest;
import com.edianniu.pscp.sps.bean.response.customer.ListResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: List
 * Author: tandingbo
 * CreateTime: 2017-07-12 10:26
 */
public class List extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(List.class);
    private int msgcode = 1002091;

    @Test
    public void test() throws IOException {
        ListRequest request = new ListRequest();
        request.setUid(1185L);
        request.setToken("79449236");

        ListResponse resp = super.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
