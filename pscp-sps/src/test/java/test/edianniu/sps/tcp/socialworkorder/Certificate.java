package test.edianniu.sps.tcp.socialworkorder;

import com.edianniu.pscp.sps.bean.request.socialworkorder.CertificateRequest;
import com.edianniu.pscp.sps.bean.response.socialworkorder.CertificateResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.edianniu.sps.tcp.AbstractLocalTest;

import java.io.IOException;

/**
 * ClassName: Certificate
 * Author: tandingbo
 * CreateTime: 2017-07-11 11:14
 */
public class Certificate extends AbstractLocalTest {
    private static final Logger log = LoggerFactory.getLogger(Certificate.class);
    private int msgcode = 1002109;

    @Test
    public void test() throws IOException {
        CertificateRequest request = new CertificateRequest();
        request.setUid(1185L);
        request.setToken("79449236");

        CertificateResponse resp = super.sendRequest(request, msgcode, CertificateResponse.class);
        log.info("result code {}", resp.getResultCode());
        log.info("result message {}", resp.getResultMessage());
    }
}
