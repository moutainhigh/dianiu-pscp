package test.edianniu.sps.tcp.electricianworkorder;

import com.edianniu.pscp.sps.bean.request.electricianworkorder.ListRequest;
import com.edianniu.pscp.sps.bean.response.electricianworkorder.ListResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: List
 * Author: tandingbo
 * CreateTime: 2017-07-11 11:35
 */
public class List extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(List.class);
    private int msgcode = 1002110;

    @Test
    public void test() throws IOException {
        ListRequest request = new ListRequest();
        request.setUid(1192L);
        request.setToken("79449236");

        request.setOffset(0);
        request.setStatus("unconfirm");
        request.setLatitude("1212.01212");
        request.setLongitude("1299.77364");

        ListResponse resp = super.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
