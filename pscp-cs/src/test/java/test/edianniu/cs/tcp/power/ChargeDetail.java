package test.edianniu.cs.tcp.power;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.power.ChargeDetailRequest;
import com.edianniu.pscp.cs.bean.response.power.ChargeDetailResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 电费详情
 * @author zhoujianjian
 * @date 2017年12月11日 下午4:02:36
 */
public class ChargeDetail extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(ChargeDetail.class);
    private int msgcode = 1002187;

    @Test
    public void test() throws IOException {
        ChargeDetailRequest request = new ChargeDetailRequest();
        request.setUid(1378L);
        request.setToken("79449236");
        request.setDate("2018-02");

        ChargeDetailResponse resp = super.sendRequest(request, msgcode, ChargeDetailResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
