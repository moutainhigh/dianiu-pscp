package test.edianniu.mis.tcp.electricianresume;

import com.edianniu.pscp.mis.bean.request.electricianresume.SetExpectedFeeRequest;
import com.edianniu.pscp.mis.bean.response.electricianresume.SetExpectedFeeResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: SetExpectedFee
 * Author: tandingbo
 * CreateTime: 2017-04-25 11:10
 */
public class SetExpectedFee extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(SetExpectedFee.class);
    private int msgcode = 1002053;

    @Test
    public void test() throws IOException {
        SetExpectedFeeRequest request = new SetExpectedFeeRequest();
        request.setUid(1039L);
        request.setToken("57388449");

        request.setResumeId(1001L);
        request.setExpectedFee("120");

        SetExpectedFeeResponse resp = super.sendRequest(request, msgcode, SetExpectedFeeResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
