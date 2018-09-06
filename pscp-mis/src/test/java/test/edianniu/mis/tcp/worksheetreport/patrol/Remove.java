package test.edianniu.mis.tcp.worksheetreport.patrol;

import com.edianniu.pscp.mis.bean.request.worksheetreport.patrol.RemoveRequest;
import com.edianniu.pscp.mis.bean.response.worksheetreport.patrol.RemoveResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Remove
 * Author: tandingbo
 * CreateTime: 2017-09-13 16:38
 */
public class Remove extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Remove.class);
    private int msgcode = 1002167;

    @Test
    public void test() throws IOException {

        RemoveRequest request = new RemoveRequest();
        request.setUid(1185L);
        request.setToken("26227159");

        request.setId(1001L);

        RemoveResponse resp = this.sendRequest(request, msgcode, RemoveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
