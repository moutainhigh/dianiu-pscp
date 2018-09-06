package test.edianniu.sps.tcp.workorder;

import com.edianniu.pscp.sps.bean.request.workOrder.SelectDataRequest;
import com.edianniu.pscp.sps.bean.response.workOrder.SelectDataResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: SelectData
 * Author: tandingbo
 * CreateTime: 2017-07-11 16:35
 */
public class SelectData extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(SelectData.class);
    private int msgcode = 1002112;

    @Test
    public void test() throws IOException {
        SelectDataRequest request = new SelectDataRequest();
        request.setUid(1185L);
        request.setToken("79449236");

        request.setOffset(0);
        request.setName("1");

        SelectDataResponse resp = super.sendRequest(request, msgcode, SelectDataResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
