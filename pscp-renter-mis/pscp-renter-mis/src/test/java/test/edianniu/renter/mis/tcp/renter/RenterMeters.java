package test.edianniu.renter.mis.tcp.renter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.renter.mis.bean.request.renter.RenterMetersRequest;
import com.edianniu.pscp.renter.mis.bean.response.renter.ListResponse;
import test.edianniu.renter.mis.tcp.AbstractLocalTest;
import java.io.IOException;


public class RenterMeters extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(RenterMeters.class);
    private int msgcode = 1002308;

    @Test
    public void test() throws IOException {
    	RenterMetersRequest request = new RenterMetersRequest();
        request.setUid(1243L);
        request.setToken("79449236");
        request.setRenterId(1170L);

        ListResponse resp = super.sendRequest(request, msgcode, ListResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
    
}
