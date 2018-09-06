package test.edianniu.cs.tcp.power;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.power.PowerFactorRequest;
import com.edianniu.pscp.cs.bean.response.power.PowerFactorResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 功率因数
 * @author zhoujianjian
 * @date 2017年12月8日 下午4:31:28
 */
public class PowerFactor extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(PowerFactor.class);
    private int msgcode = 1002185;

    @Test
    public void test() throws IOException {
        PowerFactorRequest request = new PowerFactorRequest();
        request.setUid(1243L);
        request.setToken("79449236");

        PowerFactorResponse resp = super.sendRequest(request, msgcode, PowerFactorResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
