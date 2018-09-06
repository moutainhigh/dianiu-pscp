package test.edianniu.renter.mis.tcp.renter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.renter.mis.bean.request.renter.OrderListRequest;
import com.edianniu.pscp.renter.mis.bean.response.renter.OrderListResponse;

import test.edianniu.renter.mis.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 
 * @author zhoujianjian
 * 2017年9月12日下午4:17:04
 */
public class OrderList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(OrderList.class);
    private int msgcode = 1002304;

    @Test
    public void test() throws IOException {
        OrderListRequest request = new OrderListRequest();
        request.setUid(1401L);
        request.setToken("79449236");
        request.setRenterId(1000L);

        OrderListResponse resp = super.sendRequest(request, msgcode, OrderListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
    
}
