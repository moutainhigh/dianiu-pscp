package test.edianniu.cs.tcp.power;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.power.WarningDetailRequest;
import com.edianniu.pscp.cs.bean.response.power.WarningDetailResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 报警详情
 * @author zhoujianjian
 * @date 2017年12月11日 下午6:04:54
 */
public class WarningDetail extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(WarningDetail.class);
    private int msgcode = 1002189;

    @Test
    public void test() throws IOException {
        WarningDetailRequest request = new WarningDetailRequest();
        request.setUid(1250L);
        request.setToken("79449236");
        request.setId(1002L);

        WarningDetailResponse resp = super.sendRequest(request, msgcode, WarningDetailResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
