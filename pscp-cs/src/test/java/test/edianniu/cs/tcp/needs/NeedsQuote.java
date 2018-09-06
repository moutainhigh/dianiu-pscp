package test.edianniu.cs.tcp.needs;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.request.needs.QuoteRequest;
import com.edianniu.pscp.cs.bean.response.needs.QuoteResponse;
import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 需求询价
 * @author zhoujianjian
 * 2017年9月15日上午11:22:14
 */
public class NeedsQuote extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(NeedsQuote.class);
    private int msgcode = 1002127;

    @Test
    public void test() throws IOException {
        QuoteRequest request = new QuoteRequest();
        request.setUid(1226L);
        request.setToken("79449236");
        
        request.setOrderId("NE15616201001936");
        request.setResponsedOrderIds("NEO6581442573524720,NEO6319365243343720");
       
        
        QuoteResponse resp = super.sendRequest(request, msgcode, QuoteResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
