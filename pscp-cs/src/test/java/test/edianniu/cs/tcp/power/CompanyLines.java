package test.edianniu.cs.tcp.power;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edianniu.pscp.cs.bean.request.power.CustomerLinesRequest;
import com.edianniu.pscp.cs.bean.response.power.CustomerLinesResponse;

import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 客户线路列表
 * @author zhoujianjian
 * @date 2017年12月7日 下午4:28:13
 */
public class CompanyLines extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(CompanyLines.class);
    private int msgcode = 1002190;

    @Test
    public void test() throws IOException {
        CustomerLinesRequest request = new CustomerLinesRequest();
        request.setUid(1250L);
        request.setToken("79449236");


        CustomerLinesResponse resp = super.sendRequest(request, msgcode, CustomerLinesResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
