package test.edianniu.cs.tcp.power;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.request.power.PowerDistributeRequest;
import com.edianniu.pscp.cs.bean.response.power.PowerDistributeResponse;
import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 用电量
 * @author zhoujianjian
 * @date 2017年12月7日 下午10:09:08
 */
public class PowerDistribute extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(PowerDistribute.class);
    private int msgcode = 1002184;

    @Test
    public void test() throws IOException {
        PowerDistributeRequest request = new PowerDistributeRequest();
        request.setUid(1378L);
        request.setToken("79449236");
        request.setDate("2018-02");

        PowerDistributeResponse resp = super.sendRequest(request, msgcode, PowerDistributeResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
