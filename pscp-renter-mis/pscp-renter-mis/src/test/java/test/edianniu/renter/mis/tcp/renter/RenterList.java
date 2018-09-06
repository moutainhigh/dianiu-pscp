package test.edianniu.renter.mis.tcp.renter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.renter.mis.bean.request.renter.ListRequest;
import com.edianniu.pscp.renter.mis.bean.response.renter.ListResponse;
import test.edianniu.renter.mis.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 
 * @author zhoujianjian
 * 2017年9月12日下午4:17:04
 */
public class RenterList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(RenterList.class);
    private int msgcode = 1002301;

    @Test
    public void test() throws IOException {
        ListRequest request = new ListRequest();
        request.setUid(1243L);
        request.setToken("79449236");
        //request.setType(3);

        ListResponse resp = super.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
    
}
