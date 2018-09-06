package test.edianniu.mis.tcp.defectrecord;

import com.edianniu.pscp.mis.bean.request.defectrecord.RemoveRequest;
import com.edianniu.pscp.mis.bean.response.defectrecord.RemoveResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Remove
 * Author: tandingbo
 * CreateTime: 2017-09-13 10:16
 */
public class Remove extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Remove.class);
    private int msgcode = 1002159;

    @Test
    public void test() throws IOException {

        RemoveRequest request = new RemoveRequest();
        request.setUid(1185L);
        request.setToken("26227159");

        request.setId(1000L);
        request.setOrderId("GD38941505536068");

        RemoveResponse resp = this.sendRequest(request, msgcode, RemoveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}