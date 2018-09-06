package test.edianniu.mis.tcp.electricianresume;

import com.edianniu.pscp.mis.bean.request.electricianresume.DetailRequest;
import com.edianniu.pscp.mis.bean.response.electricianresume.DetailResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: ResumeDetail
 * Author: tandingbo
 * CreateTime: 2017-04-24 14:21
 */
public class ResumeDetail extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(ResumeDetail.class);
    private int msgcode = 1002051;

    @Test
    public void test() throws IOException {
        DetailRequest request = new DetailRequest();
        request.setUid(1199L);
        request.setToken("57388449");
        DetailResponse resp = super.sendRequest(request, msgcode, DetailResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }

}
