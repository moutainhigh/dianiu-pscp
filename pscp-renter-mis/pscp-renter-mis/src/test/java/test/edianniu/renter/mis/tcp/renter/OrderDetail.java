package test.edianniu.renter.mis.tcp.renter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.renter.mis.bean.request.renter.OrderDetailRequest;
import com.edianniu.pscp.renter.mis.bean.response.renter.OrderDetailResponse;

import test.edianniu.renter.mis.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 
 * @author zhoujianjian
 * 2017年9月12日下午4:17:04
 */
public class OrderDetail extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(OrderDetail.class);
    private int msgcode = 1002305;

    @Test
    public void test() throws IOException {
        OrderDetailRequest request = new OrderDetailRequest();
        request.setUid(1549L);
        request.setToken("79449236");
        request.setId(1048L);

        OrderDetailResponse resp = super.sendRequest(request, msgcode, OrderDetailResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
    
}
