package test.edianniu.renter.mis.tcp.renter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.edianniu.pscp.renter.mis.bean.request.renter.RentersRequest;
import com.edianniu.pscp.renter.mis.bean.response.renter.RentersResponse;
import test.edianniu.renter.mis.tcp.AbstractLocalTest;
import java.io.IOException;

public class Renters extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Renters.class);
    private int msgcode = 1002307;

    @Test
    public void test() throws IOException {
        RentersRequest request = new RentersRequest();
        request.setUid(1401L);
        request.setToken("79449236");

        RentersResponse resp = super.sendRequest(request, msgcode, RentersResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
    
}
