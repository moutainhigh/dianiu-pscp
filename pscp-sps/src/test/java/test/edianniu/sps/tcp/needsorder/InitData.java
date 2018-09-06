package test.edianniu.sps.tcp.needsorder;

import com.edianniu.pscp.sps.bean.request.needsorder.InitDataRequest;
import com.edianniu.pscp.sps.bean.response.needsorder.InitDataResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: InitData
 * Author: tandingbo
 * CreateTime: 2017-09-26 18:19
 */
public class InitData extends AbstractLocalTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private int msgcode = 1002173;

    @Test
    public void test() throws IOException {
        InitDataRequest request = new InitDataRequest();
        request.setUid(1185L);
        request.setToken("32581897");

        request.setOrderId("NEO1729908703704624");

        InitDataResponse resp = super.sendRequest(request, msgcode, InitDataResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
