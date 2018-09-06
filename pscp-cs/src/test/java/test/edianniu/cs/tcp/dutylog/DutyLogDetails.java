package test.edianniu.cs.tcp.dutylog;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.request.dutylog.DetailsRequest;
import com.edianniu.pscp.cs.bean.response.dutylog.DetailsResponse;
import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 配电房值班日志详情
 * @author zhoujianjian
 * @date 2017年10月30日 下午7:27:29
 */
public class DutyLogDetails extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(DutyLogDetails.class);
    private int msgcode = 1002142;

    @Test
    public void test() throws IOException {
        DetailsRequest request = new DetailsRequest();
        request.setUid(1226L);
        request.setToken("79449236");
       
        request.setId(1000L);
        
        DetailsResponse resp = super.sendRequest(request, msgcode, DetailsResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
