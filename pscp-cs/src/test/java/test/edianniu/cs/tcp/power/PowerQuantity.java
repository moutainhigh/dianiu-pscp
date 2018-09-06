package test.edianniu.cs.tcp.power;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.request.power.PowerQuantityRequest;
import com.edianniu.pscp.cs.bean.response.power.PowerQuantityResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 用电量
 * @author zhoujianjian
 * @date 2017年12月7日 下午10:09:08
 */
public class PowerQuantity extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(PowerQuantity.class);
    private int msgcode = 1002183;

    @Test
    public void test() throws IOException {
        PowerQuantityRequest request = new PowerQuantityRequest();
        request.setUid(1221L);
        request.setDate("2018-02");
        request.setToken("79449236");

        PowerQuantityResponse resp = super.sendRequest(request, msgcode, PowerQuantityResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
