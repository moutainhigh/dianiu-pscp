package test.edianniu.sps.tcp.workorder;

import com.edianniu.pscp.sps.bean.request.workOrder.InitDataRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.InitDataResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: InitData
 * Author: tandingbo
 * CreateTime: 2017-06-28 10:51
 */
public class InitData extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(InitData.class);
    private int msgcode = 1002096;

    @Test
    public void test() throws IOException {
        InitDataRequest request = new InitDataRequest();
        request.setUid(1185L);
        request.setToken("79449236");

        InitDataResponse resp = super.sendRequest(request, msgcode, InitDataResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
