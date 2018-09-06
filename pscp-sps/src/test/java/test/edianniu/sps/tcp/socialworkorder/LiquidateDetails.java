package test.edianniu.sps.tcp.socialworkorder;

import com.edianniu.pscp.sps.bean.request.socialworkorder.LiquidateDetailsRequest;
import com.edianniu.pscp.sps.bean.response.socialworkorder.LiquidateDetailsResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: LiquidateDetails
 * Author: tandingbo
 * CreateTime: 2017-06-30 11:24
 */
public class LiquidateDetails extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(LiquidateDetails.class);
    private int msgcode = 1002106;

    @Test
    public void test() throws IOException {
        LiquidateDetailsRequest request = new LiquidateDetailsRequest();
        request.setUid(1185L);
        request.setToken("79449236");

        request.setOrderId("SGD2003304594557720");
        request.setElectricianId(1199L);

        LiquidateDetailsResponse resp = super.sendRequest(request, msgcode, LiquidateDetailsResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
