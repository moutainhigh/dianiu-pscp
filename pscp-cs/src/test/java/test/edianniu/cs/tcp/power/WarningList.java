package test.edianniu.cs.tcp.power;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.power.WarningListRequest;
import com.edianniu.pscp.cs.bean.response.power.WarningListResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 报警详情
 * @author zhoujianjian
 * @date 2017年12月11日 下午6:05:39
 */
public class WarningList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(WarningList.class);
    private int msgcode = 1002188;

    @Test
    public void test() throws IOException {
        WarningListRequest request = new WarningListRequest();
        request.setUid(1250L);
        request.setToken("79449236");

        WarningListResponse resp = super.sendRequest(request, msgcode, WarningListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
