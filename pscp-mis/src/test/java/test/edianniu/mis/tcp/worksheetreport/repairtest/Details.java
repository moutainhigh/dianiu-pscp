package test.edianniu.mis.tcp.worksheetreport.repairtest;

import com.edianniu.pscp.mis.bean.request.worksheetreport.repairtest.DetailsRequest;
import com.edianniu.pscp.mis.bean.response.worksheetreport.repairtest.DetailsResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Details
 * Author: tandingbo
 * CreateTime: 2017-09-13 16:16
 */
public class Details extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Details.class);
    private int msgcode = 1002161;

    @Test
    public void test() throws IOException {

        DetailsRequest request = new DetailsRequest();
        request.setUid(1185L);
        request.setToken("26227159");

        request.setId(1000L);

        DetailsResponse resp = this.sendRequest(request, msgcode, DetailsResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
