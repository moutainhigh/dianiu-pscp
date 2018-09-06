package test.edianniu.cs.tcp.power;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.power.ChargeBillRequest;
import com.edianniu.pscp.cs.bean.response.power.ChargeBillResponse;
import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 用电量
 * @author zhoujianjian
 * @date 2017年12月7日 下午10:09:08
 */
public class ChargeBill extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(ChargeBill.class);
    private int msgcode = 1002186;

    @Test
    public void test() throws IOException {
        ChargeBillRequest request = new ChargeBillRequest();
        request.setUid(1250L);
        request.setToken("79449236");
        request.setDate("2018-03");

        ChargeBillResponse resp = super.sendRequest(request, msgcode, ChargeBillResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
