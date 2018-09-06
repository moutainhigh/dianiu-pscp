package test.edianniu.mis.tcp.workexperience;

import com.edianniu.pscp.mis.bean.request.workexperience.SaveOrUpdateRequest;
import com.edianniu.pscp.mis.bean.response.workexperience.SaveOrUpdateResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: SaveOrUpdate
 * Author: tandingbo
 * CreateTime: 2017-04-25 11:44
 */
public class SaveOrUpdate extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(SaveOrUpdate.class);
    private int msgcode = 1002054;

    @Test
    public void test() throws IOException {
        SaveOrUpdateRequest request = new SaveOrUpdateRequest();
        request.setUid(1039L);
        request.setToken("57388449");

        request.setResumeId(1002L);
//        request.setExperienceId(1001L);
        request.setStartTime("2011-04");
        request.setEndTime("2017-03");
        request.setCompanyName("电力公司");
        request.setWorkContent("电工日常工作，搞定、阿斯顿发顺丰");

        SaveOrUpdateResponse resp = super.sendRequest(request, msgcode, SaveOrUpdateResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
