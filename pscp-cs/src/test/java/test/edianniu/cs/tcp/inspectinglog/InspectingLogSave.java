package test.edianniu.cs.tcp.inspectinglog;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.inspectinglog.SaveRequest;
import com.edianniu.pscp.cs.bean.response.inspectinglog.SaveResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 消防设施保存
 * @author zhoujianjian
 * @date 2017年11月1日 下午6:32:51
 */
public class InspectingLogSave extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(InspectingLogSave.class);
    private int msgcode = 1002135;

    @Test
    public void test() throws IOException {
        SaveRequest request = new SaveRequest();
        request.setUid(1226L);
        request.setToken("79449236");
        
        request.setEquipmentId(1003L);
        request.setType(2);
        request.setContent("test");

        SaveResponse resp = super.sendRequest(request, msgcode, SaveResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
