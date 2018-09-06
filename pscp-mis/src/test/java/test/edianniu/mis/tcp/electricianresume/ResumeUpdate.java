package test.edianniu.mis.tcp.electricianresume;

import com.edianniu.pscp.mis.bean.request.electricianresume.UpdateRequest;
import com.edianniu.pscp.mis.bean.response.electricianresume.UpdateResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: ResumeUpdate
 * Author: tandingbo
 * CreateTime: 2017-04-25 11:32
 */
public class ResumeUpdate extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(ResumeUpdate.class);
    private int msgcode = 1002052;

    @Test
    public void test() throws IOException {
        UpdateRequest request = new UpdateRequest();
        request.setUid(1039L);
        request.setToken("57388449");

        request.setResumeId(1001L);
        request.setDiploma(4);
        request.setWorkingYears(10);
        request.setCity("杭州");
        request.setSynopsis("我是快乐电工");

        UpdateResponse resp = super.sendRequest(request, msgcode, UpdateResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
