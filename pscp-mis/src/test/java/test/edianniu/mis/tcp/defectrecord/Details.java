package test.edianniu.mis.tcp.defectrecord;

import com.edianniu.pscp.mis.bean.request.defectrecord.DetailsRequest;
import com.edianniu.pscp.mis.bean.response.defectrecord.DetailsResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.mis.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Details
 * Author: tandingbo
 * CreateTime: 2017-09-12 17:23
 */
public class Details extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Save.class);
    private int msgcode = 1002156;

    @Test
    public void test() throws IOException {

        DetailsRequest request = new DetailsRequest();
        request.setUid(1356L);
        request.setToken("26227159");
        request.setId(1052L);

        DetailsResponse resp = this.sendRequest(request, msgcode, DetailsResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}