package test.edianniu.renter.mis.tcp.renter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.renter.mis.bean.request.renter.HomeRequest;
import com.edianniu.pscp.renter.mis.bean.response.renter.HomeResponse;

import test.edianniu.renter.mis.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 
 * @author zhoujianjian
 * 2017年9月12日下午4:17:04
 */
public class Home extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Home.class);
    private int msgcode = 1002303;

    @Test
    public void test() throws IOException {
        HomeRequest request = new HomeRequest();
        request.setUid(1549L);
        //request.setToken("79449236");
        request.setRenterId(1146L);

        HomeResponse resp = super.sendRequest(request, msgcode, HomeResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
    
}
