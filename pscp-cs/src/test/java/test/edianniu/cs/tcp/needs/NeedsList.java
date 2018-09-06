package test.edianniu.cs.tcp.needs;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.cs.bean.request.needs.ListRequest;
import com.edianniu.pscp.cs.bean.response.needs.ListResponse;
import test.edianniu.cs.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 获取需求列表
 * @author zhoujianjian
 * 2017年9月12日下午4:17:04
 */
public class NeedsList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(NeedsList.class);
    private int msgcode = 1002124;

    @Test
    public void test() throws IOException {
        ListRequest request = new ListRequest();
        request.setUid(1225L);
        request.setToken("79449236");
        request.setOffset(0);
        request.setStatus("verifying");
       
        
        ListResponse resp = super.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
