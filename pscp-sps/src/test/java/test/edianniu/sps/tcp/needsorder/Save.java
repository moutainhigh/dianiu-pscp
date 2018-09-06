package test.edianniu.sps.tcp.needsorder;

import com.edianniu.pscp.sps.bean.request.needsorder.SaveRequest;
import com.edianniu.pscp.sps.bean.response.needsorder.SaveResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Save
 * Author: tandingbo
 * CreateTime: 2017-09-26 11:01
 */
public class Save extends AbstractLocalTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private int msgcode = 1002171;

    @Test
    public void test() throws IOException {
        SaveRequest request = new SaveRequest();
        request.setUid(1185L);
        request.setToken("32581897");

        request.setOrderId("NE5717361578395720");

        SaveResponse resp = super.sendRequest(request, msgcode, SaveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
