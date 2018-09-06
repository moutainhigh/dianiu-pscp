package test.edianniu.renter.mis.tcp.renter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.renter.mis.bean.request.renter.SwitchRequest;
import com.edianniu.pscp.renter.mis.bean.response.renter.SwitchResponse;

import test.edianniu.renter.mis.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 
 * @author zhoujianjian
 * 2017年9月12日下午4:17:04
 */
public class Switch extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Switch.class);
    private int msgcode = 1002306;

    @Test
    public void test() throws IOException {
        SwitchRequest request = new SwitchRequest();
        request.setUid(1382L);
        request.setToken("79449236");
        request.setPwd("123456");
        request.setRenterId(1000L);
        request.setType(0);

        SwitchResponse resp = super.sendRequest(request, msgcode, SwitchResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
    
}
