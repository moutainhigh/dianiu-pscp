package test.edianniu.mis.tcp.workexperience;

import com.edianniu.pscp.mis.bean.request.workexperience.DelWorkExperienceRequest;
import com.edianniu.pscp.mis.bean.response.workexperience.DelWorkExperienceResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: DelWorkExperience
 * Author: tandingbo
 * CreateTime: 2017-04-25 14:46
 */
public class DelWorkExperience extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(DelWorkExperience.class);
    private int msgcode = 1002055;

    @Test
    public void test() throws IOException {
        DelWorkExperienceRequest request = new DelWorkExperienceRequest();
        request.setUid(1039L);
        request.setToken("57388449");

        request.setResumeId(1001L);
        request.setExperienceId(1001L);

        DelWorkExperienceResponse resp = super.sendRequest(request, msgcode, DelWorkExperienceResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
