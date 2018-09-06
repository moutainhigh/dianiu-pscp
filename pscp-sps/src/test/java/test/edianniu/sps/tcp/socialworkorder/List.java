package test.edianniu.sps.tcp.socialworkorder;

import com.edianniu.pscp.sps.bean.request.socialworkorder.ListRequest;
import com.edianniu.pscp.sps.bean.response.socialworkorder.ListResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: List
 * Author: tandingbo
 * CreateTime: 2017-06-29 11:36
 */
public class List extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(List.class);
    private int msgcode = 1002101;

    @Test
    public void test() throws IOException {
        ListRequest request = new ListRequest();
        request.setUid(1185L);
        request.setToken("32581897");

        request.setOffset(0);
        request.setStatus("recruitend");

        request.setLatitude("30.18342");
        request.setLongitude("120.1493");
        request.setTitle("11");

        ListResponse resp = super.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
