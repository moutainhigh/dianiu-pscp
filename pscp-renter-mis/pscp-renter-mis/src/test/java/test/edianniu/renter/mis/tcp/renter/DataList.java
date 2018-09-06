package test.edianniu.renter.mis.tcp.renter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.renter.mis.bean.request.renter.DataListRequest;
import com.edianniu.pscp.renter.mis.bean.response.renter.DataListResponse;
import test.edianniu.renter.mis.tcp.AbstractLocalTest;
import java.io.IOException;

/**
 * 
 * @author zhoujianjian
 * 2017年9月12日下午4:17:04
 */
public class DataList extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(DataList.class);
    private int msgcode = 1002302;

    @Test
    public void test() throws IOException {
        DataListRequest request = new DataListRequest();
        request.setUid(1382L);
        request.setToken("79449236");

        DataListResponse resp = super.sendRequest(request, msgcode, DataListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
    
}
